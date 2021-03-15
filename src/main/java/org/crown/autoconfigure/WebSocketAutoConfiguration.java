package org.crown.autoconfigure;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.PostConstruct;

import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.consolelog.ConsoleLog;
import org.crown.framework.consolelog.ConsoleLogQueue;
import org.crown.framework.manager.ThreadExecutors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * Configure the WebSocket message proxy endpoint, the stomp server
 *
 * @author Caratacus
 * @link https://cloud.tencent.com/developer/article/1096792
 */
@Slf4j
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketAutoConfiguration implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        return ShiroUtils.getSubject().isPermitted(ConsoleLog.VIEW_PERM);
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

                    }
                })
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * Push logs/topic/pullLogger
     */
    @PostConstruct
    public void pushLogger() {
        ScheduledExecutorService executorService = ThreadExecutors.getExecutorService();
        Runnable runnable = () -> {
            for (; ; ) {
                try {
                    ConsoleLog log = ConsoleLogQueue.getInstance().poll();
                    if (log != null && messagingTemplate != null) {
                        messagingTemplate.convertAndSend("/topic/consolelog", log);
                    }
                } catch (Exception e) {
                    log.warn("Failed to push log:{}", e.getMessage());
                }
            }
        };
        executorService.submit(runnable);
    }
}
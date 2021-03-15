package org.crown.autoconfigure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crown.common.utils.StringUtils;
import org.crown.framework.shiro.realm.UserRealm;
import org.crown.framework.shiro.session.OnlineSessionDAO;
import org.crown.framework.shiro.session.OnlineSessionFactory;
import org.crown.framework.shiro.web.filter.LogoutFilter;
import org.crown.framework.shiro.web.filter.UserFilter;
import org.crown.framework.shiro.web.filter.kickout.KickoutSessionFilter;
import org.crown.framework.shiro.web.filter.online.OnlineSessionFilter;
import org.crown.framework.shiro.web.filter.sync.SyncOnlineSessionFilter;
import org.crown.framework.shiro.web.session.OnlineWebSessionManager;
import org.crown.framework.shiro.web.session.SpringSessionValidationScheduler;
import org.crown.framework.spring.ApplicationUtils;
import org.crown.framework.springboot.properties.RememberMeCookie;
import org.crown.framework.springboot.properties.ShiroProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * Permission configuration loading
 *
 * @author Crown
 */
@Configuration
@EnableConfigurationProperties({ShiroProperties.class})
public class ShiroAuoConfiguration implements WebMvcConfigurer {

    private final ShiroProperties properties;

    public ShiroAuoConfiguration(ShiroProperties properties) {
        this.properties = properties;
    }

    /**
     * Cache manager implemented using Ehcache
     */
    @Bean
    public EhCacheManager getEhCacheManager() {
        net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getCacheManager("Crown2");
        EhCacheManager em = new EhCacheManager();
        if (StringUtils.isNull(cacheManager)) {
            em.setCacheManager(new net.sf.ehcache.CacheManager(getCacheManagerConfigFileInputStream()));
            return em;
        } else {
            em.setCacheManager(cacheManager);
            return em;
        }
    }

    /**
     * Return to the configuration file stream to prevent the ehcache configuration file from being occupied all the time, and the project cannot be completely destroyed and redeployed
     */
    protected InputStream getCacheManagerConfigFileInputStream() {
        String configFile = "classpath:ehcache/ehcache-shiro.xml";
        try (InputStream inputStream = ResourceUtils.getInputStreamForPath(configFile)) {
            byte[] b = IOUtils.toByteArray(inputStream);
            return new ByteArrayInputStream(b);
        } catch (IOException e) {
            throw new ConfigurationException(
                    "Unable to obtain input stream for cacheManagerConfigFile [" + configFile + "]", e);
        }
    }

    /**
     * Custom Realm
     */
    @Bean
    public UserRealm userRealm(EhCacheManager cacheManager) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCacheManager(cacheManager);
        return userRealm;
    }

    /**
     * Custom sessionDAO session
     */
    @Bean
    public OnlineSessionDAO sessionDAO() {
        return new OnlineSessionDAO(properties.getSession().getDbSyncPeriod());
    }

    /**
     * Custom sessionFactory session
     */
    @Bean
    public OnlineSessionFactory sessionFactory() {
        return new OnlineSessionFactory();
    }

    /**
     * Session manager
     */
    @Bean
    public OnlineWebSessionManager sessionManager() {
        OnlineWebSessionManager manager = new OnlineWebSessionManager();
        // Join the cache manager
        manager.setCacheManager(getEhCacheManager());
        // Delete expired session
        manager.setDeleteInvalidSessions(true);
        // Set the global session timeout
        manager.setGlobalSessionTimeout(properties.getSession().getExpireTime() * 60 * 1000);
        // Remove JSESSIONID
        manager.setSessionIdUrlRewritingEnabled(false);
        // Define invalid Session timing scheduler to be used
        manager.setSessionValidationScheduler(ApplicationUtils.getBean(SpringSessionValidationScheduler.class));
        // Whether to check the session regularly
        manager.setSessionValidationSchedulerEnabled(true);
        // Custom SessionDao
        manager.setSessionDAO(sessionDAO());
        // Custom sessionFactory
        manager.setSessionFactory(sessionFactory());
        return manager;
    }

    /**
     * Security Manager
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm, SpringSessionValidationScheduler springSessionValidationScheduler) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // Set realm.
        securityManager.setRealm(userRealm);
        // Remember me
        securityManager.setRememberMeManager(rememberMeManager());
        // Set cache manager;
        securityManager.setCacheManager(getEhCacheManager());
        // Session manager
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * Exit filter
     */
    public LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setCacheManager(getEhCacheManager());
        logoutFilter.setLoginUrl(properties.getLoginUrl());
        return logoutFilter;
    }

    /**
     * UserFilter
     */
    public UserFilter userFilter() {
        return new UserFilter();
    }

    /**
     * Shiro filter configuration
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro's core security interface, this attribute is required
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // If the identity authentication fails, jump to the configuration of the login page
        shiroFilterFactoryBean.setLoginUrl(properties.getLoginUrl());
        // If permission authentication fails, jump to the specified page
        shiroFilterFactoryBean.setUnauthorizedUrl(properties.getUnauthUrl());
        // Shiro connection constraint configuration, that is, the definition of the filter chain
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // Set up anonymous access to static resources
        filterChainDefinitionMap.put("/favicon.ico**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/docs/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/ajax/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/crown/**", "anon");
        // Exit the logout address, shiro to clear the session
        filterChainDefinitionMap.put("/logout", "logout");
        // Access that does not need to be intercepted
        filterChainDefinitionMap.put("/login", "anon");
        //Verification code
        filterChainDefinitionMap.put("/captcha", "anon");

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("onlineSession", onlineSessionFilter());
        filters.put("syncOnlineSession", syncOnlineSessionFilter());
        filters.put("kickout", kickoutSessionFilter());
        // Logout is successful, then jump to the specified page
        filters.put("logout", logoutFilter());
        filters.put("user", userFilter());
        shiroFilterFactoryBean.setFilters(filters);

        // All requests require authentication
        filterChainDefinitionMap.put("/**", "user,kickout,onlineSession,syncOnlineSession");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * Custom online user processing filter
     */
    @Bean
    public OnlineSessionFilter onlineSessionFilter() {
        OnlineSessionFilter onlineSessionFilter = new OnlineSessionFilter();
        onlineSessionFilter.setLoginUrl(properties.getLoginUrl());
        return onlineSessionFilter;
    }

    /**
     * Customize online user synchronization filter
     */
    @Bean
    public SyncOnlineSessionFilter syncOnlineSessionFilter() {
        return new SyncOnlineSessionFilter();
    }

    /**
     * cookie attribute settings
     */
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        RememberMeCookie rememberMeCookie = properties.getRememberMeCookie();
        cookie.setDomain(rememberMeCookie.getDomain());
        cookie.setPath(rememberMeCookie.getPath());
        cookie.setHttpOnly(rememberMeCookie.isHttpOnly());
        cookie.setMaxAge(rememberMeCookie.getMaxAge() * 24 * 60 * 60);
        return cookie;
    }

    /**
     * remember me
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        /**
         * Cipherkey byte length Must equal 16
         */
        cookieRememberMeManager.setCipherKey(Base64.decode("CrownKey==a12d/dakdad"));
        return cookieRememberMeManager;
    }

    /**
     * Multi-device login restriction for the same user
     */
    public KickoutSessionFilter kickoutSessionFilter() {
        KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
        kickoutSessionFilter.setCacheManager(getEhCacheManager());
        kickoutSessionFilter.setSessionManager(sessionManager());
        // The maximum number of sessions for the same user, the default is -1 without limit; for example, 2 means that the same user allows up to two people to log in at the same time
        kickoutSessionFilter.setMaxSession(properties.getSession().getMaxSession());
        // Whether to kick out those who log in later, the default is false; that is, the user who logs later will kick out the user who logs in before
        kickoutSessionFilter.setKickoutAfter(properties.getSession().isKickoutAfter());
        // The address to be redirected to after being kicked out
        kickoutSessionFilter.setKickoutUrl("/login?kickout=1");
        return kickoutSessionFilter;
    }

    /**
     * Integration of thymeleaf template engine and shiro framework
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * Turn on Shiro annotation notifier
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * The default homepage setting, when you enter the domain name, you can automatically jump to the default specified webpage
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:" + properties.getIndexUrl());
    }

}


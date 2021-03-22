package org.crown.project.monitor.online.domain;

import java.util.Date;

import org.crown.common.enums.OnlineStatus;
import org.crown.framework.web.domain.BaseQueryParams;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

/**
 * Current online session sys_user_online
 *
 * @author Crown
 */
@Setter
@Getter
public class UserOnline extends BaseQueryParams {

    private static final long serialVersionUID = 1L;

    /**
     * User session id
     */
    @TableId(value = "sessionId", type = IdType.INPUT)
    private String sessionId;

    /**
     * Department name
     */
    private String deptName;

    /**
     * Login name
     */
    private String loginName;

    /**
     * Login IP address
     */
    private String ipaddr;

    /**
     * Login address
     */
    private String loginLocation;

    /**
     * Browser type
     */
    private String browser;

    /**
     * Operating system
     */
    private String os;

    /**
     * session creation time
     */
    private Date startTimestamp;

    /**
     * session last access time
     */
    private Date lastAccessTime;

    /**
     * Timeout period, in minutes
     */
    private Long expireTime;

    /**
     * online status
     */
    private OnlineStatus status = OnlineStatus.on_line;

    /**
     * Current user session backed up
     */
    @TableField(exist = false)
    private OnlineSession session;

}

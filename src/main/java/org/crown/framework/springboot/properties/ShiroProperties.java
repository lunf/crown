package org.crown.framework.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Shiro related configuration
 *
 * @author Caratacus
 */
@ConfigurationProperties(prefix = ShiroProperties.SHIRO_PREFIX)
@Setter
@Getter
public class ShiroProperties {

    public static final String SHIRO_PREFIX = "shiro";

    /**
     * Login address
     */
    private String loginUrl;
    /**
     * Permission authentication failed address
     */
    private String unauthUrl;
    /**
     * Home address
     */
    private String indexUrl;
    /**
     * remember me
     */
    @NestedConfigurationProperty
    private RememberMeCookie rememberMeCookie;
    /**
     * Session configuration
     */
    @NestedConfigurationProperty
    private Session session;
}

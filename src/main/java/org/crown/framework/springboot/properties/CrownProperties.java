package org.crown.framework.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Crown project related configuration
 *
 * @author Caratacus
 */
@ConfigurationProperties(prefix = CrownProperties.CROWN_PREFIX)
@Setter
@Getter
public class CrownProperties {

    public static final String CROWN_PREFIX = "crown";

    /**
     * Example demo switch
     */
    @NestedConfigurationProperty
    private Demo demo;

    /**
     * path
     */
    @NestedConfigurationProperty
    private Path path;

    /**
     * Get address switch
     */
    @NestedConfigurationProperty
    private Address address;
    /**
     * User password configuration
     */
    @NestedConfigurationProperty
    private Password password;
    /**
     * Generate code configuration
     */
    @NestedConfigurationProperty
    private Generator generator;
    /**
     * Xss configuration
     */
    @NestedConfigurationProperty
    private Xss xss;
    /**
     * Email notification configuration
     */
    @NestedConfigurationProperty
    private Email email;

}

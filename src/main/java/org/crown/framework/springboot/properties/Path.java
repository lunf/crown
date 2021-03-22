package org.crown.framework.springboot.properties;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Path {

    /**
     * Upload path
     */
    private String filePath;
    /**
     * Resource Handler
     */
    private String resourceHandler;
    /**
     * Resource path
     */
    private String resourcePath;
    /**
     * Prefix object
     */
    @NestedConfigurationProperty
    private Prefix prefix;
}
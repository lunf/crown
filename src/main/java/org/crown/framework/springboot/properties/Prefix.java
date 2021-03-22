package org.crown.framework.springboot.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Prefix {

    /**
     * Avatar upload path prefix
     */
    private String avatar = "avatar/";
    /**
     * Download path prefix
     */
    private String download = "download/";
    /**
     * Upload path prefix
     */
    private String upload = "upload/";
}
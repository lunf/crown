package org.crown.framework.springboot.properties;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Xss {

    /**
     * Xss switch
     */
    private Boolean enabled;

    /**
     * Exclude fields
     */
    private List<String> excludeFields;
    /**
     * Exclude path
     */
    private List<String> excludeUrls;

}
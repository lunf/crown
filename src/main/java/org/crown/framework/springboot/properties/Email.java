package org.crown.framework.springboot.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Email {

    /**
     * switch
     */
    private boolean enabled = false;
    /**
     * Email address
     */
    private String send;
}
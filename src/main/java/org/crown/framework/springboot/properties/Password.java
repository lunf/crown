package org.crown.framework.springboot.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Password {

    /**
     * Maximum number of incorrect password entries
     */
    private int maxRetryCount = 10;
}
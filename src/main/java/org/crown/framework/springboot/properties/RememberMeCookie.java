package org.crown.framework.springboot.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RememberMeCookie {

    /**
     * Set the cookie domain name. The default is empty, that is, the domain name you are currently visiting
     */
    private String domain;
    /**
     * Set the effective access path of the cookie
     */
    private String path;
    /**
     * Set the HttpOnly attribute
     */
    private boolean httpOnly;
    /**
     * Set the expiration time of the cookie, in days
     */
    private int maxAge;
}

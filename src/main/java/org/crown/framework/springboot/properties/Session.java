package org.crown.framework.springboot.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Session {

    /**
     * # Session timeout, -1 means never expire (default 30 minutes)
     */
    private int expireTime;
    /**
     * Period of synchronizing session to database (minutes)
     */
    private int dbSyncPeriod;
    /**
     * How often to check the validity of the session, (minutes)
     */
    private int validationInterval;
    /**
     * The maximum number of sessions for the same user, for example, 2 means that the same account allows up to two people to log in at the same time (-1 means unlimited)
     */
    private int maxSession;
    /**
     * Kick out the users who have logged in before/after login, by default the users who have logged in before are kicked out
     */
    private boolean kickoutAfter;
}

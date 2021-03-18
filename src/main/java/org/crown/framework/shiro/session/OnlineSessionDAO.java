package org.crown.framework.shiro.session;

import java.io.Serializable;
import java.util.Date;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.crown.common.enums.OnlineStatus;
import org.crown.framework.manager.ThreadExecutors;
import org.crown.framework.manager.factory.TimerTasks;
import org.crown.project.monitor.online.domain.OnlineSession;
import org.crown.project.monitor.online.domain.UserOnline;
import org.crown.project.monitor.online.service.IUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * db operation for custom ShiroSession
 *
 * @author Crown
 */
public class OnlineSessionDAO extends EnterpriseCacheSessionDAO {

    /**
     * The period for synchronizing the session to the database, in milliseconds (default 1 minute)
     */
    private int dbSyncPeriod;

    /**
     * Timestamp of the last time the database was synchronized
     */
    private static final String LAST_SYNC_DB_TIMESTAMP = OnlineSessionDAO.class.getName() + "LAST_SYNC_DB_TIMESTAMP";

    @Autowired
    private IUserOnlineService onlineService;

    @Autowired
    private OnlineSessionFactory onlineSessionFactory;

    public OnlineSessionDAO() {
        super();
    }

    public OnlineSessionDAO(int dbSyncPeriod) {
        super();
        this.dbSyncPeriod = dbSyncPeriod;
    }

    /**
     * Get the session based on the session ID
     *
     * @param sessionId Session ID
     * @return ShiroSession
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        UserOnline userOnline = onlineService.selectOnlineById(String.valueOf(sessionId));
        if (userOnline == null) {
            return null;
        }
        return onlineSessionFactory.createSession(userOnline);
    }

    /**
     * Update the session; such as update the last access time of the session/stop the session/set the timeout time/set the removal property, etc. will be called
     */
    public void syncToDb(OnlineSession onlineSession) {
        Date lastSyncTimestamp = (Date) onlineSession.getAttribute(LAST_SYNC_DB_TIMESTAMP);
        if (lastSyncTimestamp != null) {
            boolean needSync = true;
            long deltaTime = onlineSession.getLastAccessTime().getTime() - lastSyncTimestamp.getTime();
            if (deltaTime < dbSyncPeriod * 60 * 1000) {
                //Insufficient time difference, no need to synchronize
                needSync = false;
            }
            // isGuest = true visitor
            boolean isGuest = onlineSession.getUserId() == null || onlineSession.getUserId() == 0L;

            // session The data has changed sync
            if (!isGuest && onlineSession.isAttributeChanged()) {
                needSync = true;
            }

            if (!needSync) {
                return;
            }
        }
        // Update the last synchronization database time
        onlineSession.setAttribute(LAST_SYNC_DB_TIMESTAMP, onlineSession.getLastAccessTime());
        // After the update, reset the logo
        if (onlineSession.isAttributeChanged()) {
            onlineSession.resetAttributeChanged();
        }
        ThreadExecutors.execute(TimerTasks.syncSession(onlineSession));
    }

    /**
     * When the session expires/stops (such as when the user logs out) properties, etc. will be called
     */
    @Override
    protected void doDelete(Session session) {
        OnlineSession onlineSession = (OnlineSession) session;
        if (null == onlineSession) {
            return;
        }
        onlineSession.setStatus(OnlineStatus.off_line);
        onlineService.removeById(onlineSession.getId());
    }
}

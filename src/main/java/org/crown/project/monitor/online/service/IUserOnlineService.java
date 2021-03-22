package org.crown.project.monitor.online.service;

import java.util.Date;
import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.monitor.online.domain.UserOnline;

/**
 * Online user service layer
 *
 * @author Crown
 */
public interface IUserOnlineService extends BaseService<UserOnline> {

    /**
     * Query information by session number
     *
     * @param sessionId Session id
     * @return Online user information
     */
    UserOnline selectOnlineById(String sessionId);

    /**
     * Query session collection
     *
     * @param userOnline Paging parameters
     * @return Conversation collection
     */
    List<UserOnline> selectUserOnlineList(UserOnline userOnline);

    /**
     * Forcibly log out users
     *
     * @param sessionId Session id
     */
    void forceLogout(String sessionId);

    /**
     * Query session collection
     *
     * @param expiredDate Valid period
     * @return Conversation collection
     */
    List<UserOnline> selectOnlineByExpired(Date expiredDate);
}

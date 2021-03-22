package org.crown.project.monitor.online.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.monitor.online.domain.UserOnline;

/**
 * Online users Data layer
 *
 * @author Crown
 */
@Mapper
public interface UserOnlineMapper extends BaseMapper<UserOnline> {

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
     * @param userOnline Session parameters
     * @return Conversation collection
     */
    List<UserOnline> selectUserOnlineList(UserOnline userOnline);

    /**
     * Query the collection of expired sessions
     *
     * @param lastAccessTime expire date
     * @return Conversation collection
     */
    List<UserOnline> selectOnlineByExpired(String lastAccessTime);
}

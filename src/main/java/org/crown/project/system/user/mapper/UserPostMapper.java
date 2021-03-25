package org.crown.project.system.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.user.domain.UserPost;

/**
 * User and position table Data layer
 *
 * @author Crown
 */
@Mapper
public interface UserPostMapper extends BaseMapper<UserPost> {

    /**
     * Add user post information in batches
     *
     * @param userPostList User role list
     * @return result
     */
    int batchUserPost(List<UserPost> userPostList);
}

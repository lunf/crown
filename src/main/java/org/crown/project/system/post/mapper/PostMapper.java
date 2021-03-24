package org.crown.project.system.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.post.domain.Post;

/**
 * Position information Data layer
 *
 * @author Crown
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

    /**
     * Query position based on user ID
     *
     * @param userId User ID
     * @return Job list
     */
    List<Post> selectPostsByUserId(Long userId);

}

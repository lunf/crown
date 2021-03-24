package org.crown.project.system.post.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.system.post.domain.Post;

/**
 * Job information service layer
 *
 * @author Crown
 */
public interface IPostService extends BaseService<Post> {

    /**
     * Query job information collection
     *
     * @param post Job information
     * @return Post information collection
     */
    List<Post> selectPostList(Post post);

    /**
     * Query position based on user ID
     *
     * @param userId User ID
     * @return Job list
     */
    List<Post> selectAllPostsByUserId(Long userId);

    /**
     * Query position based on user ID
     *
     * @param userId User ID
     * @return Job list
     */
    List<Post> selectPostsByUserId(Long userId);

    /**
     * Delete job information in bulk
     *
     * @param ids ID of the data to be deleted
     * @return result
     * @throws Exception abnormal
     */
    boolean deletePostByIds(String ids);

    /**
     * Check post name
     *
     * @param post Job information
     * @return result
     */
    boolean checkPostNameUnique(Post post);

    /**
     * Check post code
     *
     * @param post Job information
     * @return result
     */
    boolean checkPostCodeUnique(Post post);
}

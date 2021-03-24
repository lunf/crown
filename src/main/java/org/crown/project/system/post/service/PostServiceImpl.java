package org.crown.project.system.post.service;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.utils.StringUtils;
import org.crown.common.utils.TypeUtils;
import org.crown.framework.exception.Crown2Exception;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.project.system.post.domain.Post;
import org.crown.project.system.post.mapper.PostMapper;
import org.crown.project.system.user.domain.UserPost;
import org.crown.project.system.user.service.IUserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Job information service layer processing
 *
 * @author Crown
 */
@Service
public class PostServiceImpl extends BaseServiceImpl<PostMapper, Post> implements IPostService {

    @Autowired
    private IUserPostService userPostService;

    @Override
    public List<Post> selectPostList(Post post) {
        return query().like(StringUtils.isNotEmpty(post.getPostCode()), Post::getPostCode, post.getPostCode())
                .eq(StringUtils.isNotEmpty(post.getStatus()), Post::getStatus, post.getStatus())
                .like(StringUtils.isNotEmpty(post.getPostName()), Post::getPostName, post.getPostName())
                .list();
    }

    @Override
    public List<Post> selectAllPostsByUserId(Long userId) {
        List<Post> userPosts = selectPostsByUserId(userId);
        List<Post> posts = list();
        for (Post post : posts) {
            for (Post userRole : userPosts) {
                if (post.getPostId().longValue() == userRole.getPostId().longValue()) {
                    post.setFlag(true);
                    break;
                }
            }
        }
        return posts;
    }

    @Override
    public List<Post> selectPostsByUserId(Long userId) {
        return baseMapper.selectPostsByUserId(userId);
    }

    @Override
    public boolean deletePostByIds(String ids) {
        List<Long> postIds = StringUtils.split2List(ids, TypeUtils::castToLong);
        for (Long postId : postIds) {
            Post post = getById(postId);
            if (userPostService.query().eq(UserPost::getPostId, postId).exist()) {
                throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, post.getPostName() + "Allocated and cannot be deleted");
            }
        }
        return delete().in(Post::getPostId, postIds).execute();
    }

    @Override
    public boolean checkPostNameUnique(Post post) {
        Long postId = post.getPostId();
        Post info = query().eq(Post::getPostName, post.getPostName()).getOne();
        return Objects.isNull(info) || info.getPostId().equals(postId);
    }

    @Override
    public boolean checkPostCodeUnique(Post post) {
        Long postId = post.getPostId();
        Post info = query().eq(Post::getPostCode, post.getPostCode()).getOne();
        return Objects.isNull(info) || info.getPostId().equals(postId);

    }
}

package org.crown.project.system.user.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * User and post association sys_user_post
 *
 * @author Crown
 */
@Setter
@Getter
public class UserPost {

    /**
     * User ID
     */
    private Long userId;
    /**
     * Post ID
     */
    private Long postId;

}

package org.crown.project.system.user.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * User and role association sys_user_role
 *
 * @author Crown
 */
@Setter
@Getter
public class UserRole {

    /**
     * User ID
     */
    private Long userId;

    /**
     * Role ID
     */
    private Long roleId;

}

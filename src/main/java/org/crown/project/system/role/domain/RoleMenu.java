package org.crown.project.system.role.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Role and menu association sys_role_menu
 *
 * @author Crown
 */
@Setter
@Getter
public class RoleMenu {

    /**
     * Role ID
     */
    private Long roleId;

    /**
     * Menu ID
     */
    private Long menuId;

}

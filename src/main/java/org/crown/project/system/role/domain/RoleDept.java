package org.crown.project.system.role.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Role and department association sys_role_dept
 *
 * @author Crown
 */
@Setter
@Getter
public class RoleDept {

    /**
     * Role ID
     */
    private Long roleId;

    /**
     * Department ID
     */
    private Long deptId;

}

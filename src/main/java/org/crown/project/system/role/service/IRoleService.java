package org.crown.project.system.role.service;

import java.util.List;
import java.util.Set;

import org.crown.framework.service.BaseService;
import org.crown.project.system.role.domain.Role;
import org.crown.project.system.user.domain.UserRole;

/**
 * Role business layer
 *
 * @author Crown
 */
public interface IRoleService extends BaseService<Role> {

    /**
     * Paging query role data based on conditions
     *
     * @param role Role information
     * @return Role data collection information
     */
    List<Role> selectRoleList(Role role);

    /**
     * Query role based on user ID
     *
     * @param userId User ID
     * @return Permission list
     */
    Set<String> selectRoleKeys(Long userId);

    /**
     * Query role based on user ID
     *
     * @param userId User ID
     * @return Role list
     */
    List<Role> selectRolesByUserId(Long userId);

    /**
     * Query all roles
     *
     * @return Role list
     */
    List<Role> selectRoleAll();

    /**
     * Delete role by role ID
     *
     * @param roleId Role ID
     * @return result
     */
    boolean deleteRoleById(Long roleId);

    /**
     * Delete role user information in batches
     *
     * @param ids ID of the data to be deleted
     * @return result
     * @throws Exception abnormal
     */
    boolean deleteRoleByIds(String ids);

    /**
     * Added save role information
     *
     * @param role Role information
     * @return result
     */
    boolean insertRole(Role role);

    /**
     * Modify data permission information
     *
     * @param role Role information
     * @return result
     */
    boolean authDataScope(Role role);

    /**
     * Modify and save role information
     *
     * @param role Role information
     * @return result
     */
    boolean updateRole(Role role);

    /**
     * Verify that the role name is unique
     *
     * @param role Role information
     * @return result
     */
    boolean checkRoleNameUnique(Role role);

    /**
     * Verify that the role permissions are unique
     *
     * @param role Role information
     * @return result
     */
    boolean checkRoleKeyUnique(Role role);

    /**
     * Role status modification
     *
     * @param role Role information
     * @return result
     */
    boolean changeStatus(Role role);

    /**
     * Cancel authorized user role
     *
     * @param userRole User and role association information
     * @return result
     */
    boolean deleteAuthUser(UserRole userRole);

    /**
     * Cancel authorized user roles in batches
     *
     * @param roleId  Role ID
     * @param userIds User data ID to be deleted
     * @return result
     */
    boolean deleteAuthUsers(Long roleId, String userIds);

    /**
     * Batch selection of authorized user roles
     *
     * @param roleId  Role ID
     * @param userIds User data ID to be deleted
     * @return result
     */
    boolean insertAuthUsers(Long roleId, String userIds);
}

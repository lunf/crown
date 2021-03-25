package org.crown.project.system.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.role.domain.Role;

/**
 * Role table Data layer
 *
 * @author Crown
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

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
     * @return Role list
     */
    List<Role> selectRolesByUserId(Long userId);

}

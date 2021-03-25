package org.crown.project.system.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.user.domain.UserRole;

/**
 * User table data layer
 *
 * @author Crown
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

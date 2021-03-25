package org.crown.project.system.role.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.role.domain.RoleMenu;

/**
 * Role and menu association table Data layer
 *
 * @author Crown
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}

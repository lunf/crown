package org.crown.project.system.role.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.role.domain.RoleDept;

/**
 * Role and department association table Data layer
 *
 * @author Crown
 */
@Mapper
public interface RoleDeptMapper extends BaseMapper<RoleDept> {

}

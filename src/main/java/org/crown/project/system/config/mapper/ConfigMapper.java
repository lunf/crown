package org.crown.project.system.config.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.config.domain.Config;

/**
 * Parameter configuration data layer
 *
 * @author Crown
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {

}
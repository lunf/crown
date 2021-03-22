package org.crown.project.monitor.operlog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.monitor.operlog.domain.OperLog;

/**
 * Operation log Data layer
 *
 * @author Crown
 */
@Mapper
public interface OperLogMapper extends BaseMapper<OperLog> {

}
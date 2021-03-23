package org.crown.project.monitor.quartz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.monitor.quartz.domain.JobLog;

/**
 * <p>
 * Scheduled task log Mapper interface
 * </p>
 *
 * @author Caratacus
 */
@Mapper
public interface JobLogMapper extends BaseMapper<JobLog> {

}

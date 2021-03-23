package org.crown.project.monitor.quartz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.monitor.quartz.domain.Job;

/**
 * <p>
 * Scheduled task Mapper interface
 * </p>
 *
 * @author Caratacus
 */
@Mapper
public interface JobMapper extends BaseMapper<Job> {

}

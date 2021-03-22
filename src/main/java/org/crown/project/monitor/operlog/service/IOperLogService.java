package org.crown.project.monitor.operlog.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.monitor.operlog.domain.OperLog;

/**
 * Operation log service layer
 *
 * @author Crown
 */
public interface IOperLogService extends BaseService<OperLog> {

    /**
     * Query system operation log collection
     *
     * @param operLog Operation log object
     * @return Operation log collection
     */
    List<OperLog> selectOperLogList(OperLog operLog);

}

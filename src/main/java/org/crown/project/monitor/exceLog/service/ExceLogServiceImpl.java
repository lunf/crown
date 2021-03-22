package org.crown.project.monitor.exceLog.service;

import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.project.monitor.exceLog.domain.ExceLog;
import org.crown.project.monitor.exceLog.mapper.ExceLogMapper;
import org.springframework.stereotype.Service;

/**
 * Exception log service layer implementation
 *
 * @author Caratacus
 */
@Service
public class ExceLogServiceImpl extends BaseServiceImpl<ExceLogMapper, ExceLog> implements IExceLogService {

}

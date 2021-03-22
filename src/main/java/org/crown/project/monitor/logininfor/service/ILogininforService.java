package org.crown.project.monitor.logininfor.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.monitor.logininfor.domain.Logininfor;

/**
 * System access log information service layer
 *
 * @author Crown
 */
public interface ILogininforService extends BaseService<Logininfor> {

    /**
     * Query system login log collection
     *
     * @param logininfor Access log object
     * @return Login record collection
     */
    List<Logininfor> selectLogininforList(Logininfor logininfor);

}

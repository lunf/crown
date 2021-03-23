package org.crown.project.monitor.quartz.common;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 * Timing task execution interface
 * </p>
 *
 * @author Caratacus
 */
public interface IExecuteQuartzJob {

    /**
     * Execute immediately
     *
     * @param jobId
     * @param params
     */
    void execute(Long jobId, JSONObject params);

}

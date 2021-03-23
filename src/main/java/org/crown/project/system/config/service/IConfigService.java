package org.crown.project.system.config.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.system.config.domain.Config;

/**
 * Parameter configuration service layer
 *
 * @author Crown
 */
public interface IConfigService extends BaseService<Config> {

    /**
     * Query parameter configuration information based on key name
     *
     * @param configKey Parameter key name
     * @return Parameter key value
     */
    Config selectConfigByKey(String configKey);

    /**
     * Query parameter configuration list
     *
     * @param config Parameter configuration information
     * @return Parameter configuration collection
     */
    List<Config> selectConfigList(Config config);

    /**
     * Verify that the parameter key name is unique
     *
     * @param config Parameter information
     * @return result
     */
    boolean checkConfigKeyUnique(Config config);

    /**
     * Get value according to config key
     *
     * @param configkey
     * @return result
     */
    String getConfigValueByKey(String configkey);
}

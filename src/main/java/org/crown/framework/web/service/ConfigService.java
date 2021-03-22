package org.crown.framework.web.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.crown.project.system.config.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * html calls thymeleaf to implement parameter management
 *
 * @author Crown
 */
@Service("config")
public class ConfigService {

    private final List<String> skins = Arrays.asList("skin-blue", "skin-green", "skin-purple", "skin-red", "skin-yellow");

    @Autowired
    private IConfigService configService;

    /**
     * Query parameter configuration information based on key name
     *
     * @param configKey parameter name
     * @return Parameter key value
     */
    public String getKey(String configKey) {
        return configService.getConfigValueByKey(configKey);
    }

    /**
     * Query the background skin based on the key name
     *
     * @param skinConfigKey Skin parameter name
     * @return Parameter key value
     */
    public String getSkinKey(String skinConfigKey) {
        String configValue = getKey(skinConfigKey);
        return configValue.equals("skin-random") ? skins.get(RandomUtils.nextInt(0, 4)) : configValue;
    }

}

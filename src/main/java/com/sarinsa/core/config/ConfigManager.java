package com.sarinsa.core.config;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final Map<String, AbstractConfig> configs = new HashMap<>();





    public void registerConfig(AbstractConfig config) {
        String name = config.getName();

        if (configs.containsKey(name)) {
            throw new IllegalArgumentException("Tried to register a config with duplicate name: " + name);
        }
        configs.put(name, config);
    }

    public void initConfigs() {
        for (AbstractConfig config : configs.values()) {
            config.build();
        }
    }
}

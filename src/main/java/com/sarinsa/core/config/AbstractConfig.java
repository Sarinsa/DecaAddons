package com.sarinsa.core.config;

import com.sarinsa.core.DecaAddons;
import com.sarinsa.core.config.value.AbstractConfigField;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class AbstractConfig {

    private final String configName;
    private File configFile = null;
    private FileConfiguration config;

    private List<AbstractConfigField<?>> fields;


    public AbstractConfig(String name) {
        this.configName = name;

        try {
            this.configFile = new File(DecaAddons.INSTANCE.getDataFolder(), name + ".yml");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void build();

    public final void save() {
        try {
            config.save(configName);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void load() {

    }

    public final FileConfiguration get() {
        return config;
    }

    public final String getName() {
        return configName;
    }
}

package com.sarinsa.core;

import com.sarinsa.commands.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class DecaAddons extends JavaPlugin {

    public static DecaAddons INSTANCE;
    public static FileConfiguration CONFIG;
    public static FileConfiguration PLAYER_PROPS;

    public static Economy economy;

    public static double guardianBaseCost;
    public static double guardianDonorCost;


    @Override
    @SuppressWarnings("all")
    public void onEnable() {
        INSTANCE = this;

        if (!setupEconomy()) {
            getServer().getPluginManager().disablePlugin(this);
        }
        this.getServer().getPluginManager().registerEvents(new DAListener(), this);

        this.createDir();
        this.createConfigurations();

        this.getCommand("ride").setExecutor(new RideExecutor());
        this.getCommand("skullride").setExecutor(new ProjectileRideExecutor());
        this.getCommand("fireride").setExecutor(new ProjectileRideExecutor());
        this.getCommand("guardians").setExecutor(new GuardiansExecutor());
        this.getCommand("bottlexp").setExecutor(new BottleExpExecutor());
        this.getCommand("nopickup").setExecutor(new NoPickupExecutor());
        this.getCommand("punch").setExecutor(new PunchExecutor());
        this.getCommand("fakejoin").setExecutor(new FakeJoinLeaveExecutor());
        this.getCommand("fakeleave").setExecutor(new FakeJoinLeaveExecutor());
        this.getCommand("decaaddons").setExecutor(new DecaAddonsExecutor());

        CONFIG = getConfiguration("main.yml");
        PLAYER_PROPS = getConfiguration("playerProps.yml");

        guardianBaseCost = CONFIG.getDouble("guardian-cost");
        guardianDonorCost = CONFIG.getDouble("guardian-donor-cost");
    }

    @Override
    public void onDisable() {
        saveConfiguration("main.yml", CONFIG);
        saveConfiguration("playerProps.yml", PLAYER_PROPS);
    }

    private boolean setupEconomy() {
        try {
            RegisteredServiceProvider<Economy> serviceProvider = getServer().getServicesManager().getRegistration(Economy.class);

            if (serviceProvider == null) {
                getLogger().info("No registered economy found! Disabling...");
                return false;
            }
            else {
                economy = serviceProvider.getProvider();
                return true;
            }
        }
        catch(UnknownDependencyException e) {
            return false;
        }
    }

    public FileConfiguration getConfiguration(String configFile) {
        File config = new File(getDataFolder(), configFile);
        return YamlConfiguration.loadConfiguration(config);
    }

    public void saveConfiguration(String configFile, FileConfiguration fileConfiguration) {
        File config = new File(getDataFolder(), configFile);

        if (fileConfiguration == null) {
            throw new IllegalStateException("Tried saving a null config instance. File: " + configFile);
        }

        try {
            fileConfiguration.save(config);
        }
        catch (IOException e) {
            getLogger().log(Level.WARNING, "Failed to save configuration file {0}", configFile);
        }
    }

    private void createDir() {
        if (!getDataFolder().exists()) {
            if (!getDataFolder().mkdirs())
                getLogger().warning("Failed to create config directory.");
        }
    }

    private void createConfigurations() {
        try {
            File main = new File(getDataFolder(), "main.yml");

            if (!main.exists()) {
                getLogger().log(Level.INFO, "Creating configuration: {0}", main.getName());

                CONFIG = YamlConfiguration.loadConfiguration(main);

                CONFIG.set("punch_world_name", "world_the_end");
                CONFIG.set("punch_location", "0,0,0");
                CONFIG.set("guardian_cost_standard", "5000");
                CONFIG.set("guardian_cost_donor", "3000");

                CONFIG.save(main);
            }
            File playerProperties = new File(getDataFolder(), "playerProps.yml");

            if (!playerProperties.exists()) {
                getLogger().log(Level.INFO, "Creating configuration: {0}", playerProperties.getName());

                PLAYER_PROPS = YamlConfiguration.loadConfiguration(playerProperties);
                PLAYER_PROPS.save(playerProperties);
            }
        }
        catch (IOException e) {
            getLogger().severe("Failed to construct configuration files!");
            getLogger().severe("Sarinsa done goofed, huh? Welp, might as well blame gigo.");
        }
    }
}

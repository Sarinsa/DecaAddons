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
    public static FileConfiguration mainConfig;
    public static FileConfiguration playerProps;

    public static NamespacedKey KEY_DECA;

    public static Economy economy;

    public static double guardianBaseCost;
    public static double guardianDonorCost;


    @Override
    public void onEnable() {
        INSTANCE = this;
        KEY_DECA = new NamespacedKey(INSTANCE, "deca");

        if (!setupEconomy()) {
            getLogger().severe("Disabled due to no Vault dependency found!");
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

        mainConfig = getConfiguraion("main.yml");
        playerProps = getConfiguraion("playerProps.yml");

        guardianBaseCost = mainConfig.getDouble("guardian-cost");
        guardianDonorCost = mainConfig.getDouble("guardian-donor-cost");
    }




    @Override
    public void onDisable() {
        saveConfiguraion("main.yml", mainConfig);
        saveConfiguraion("playerProps.yml", playerProps);
    }



    private boolean setupEconomy() {
        try
        {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

            if (rsp == null) {
                getLogger().info("No registered economy found! DecaAddons will not disable itself.");
                return false;
            }
            else
            {
                economy = rsp.getProvider();
                getLogger().info("Economy successfully registered!");
                return true;
            }
        }
        catch(UnknownDependencyException e) {
            return false;
        }
    }

    public FileConfiguration getConfiguraion(String configFile) {
        File config = new File(getDataFolder(), configFile);
        return YamlConfiguration.loadConfiguration(config);
    }

    public void saveConfiguraion(String configFile, FileConfiguration fileConfiguration) {

        File config = new File(getDataFolder(), configFile);

        try {
            fileConfiguration.save(config);
        }
        catch (IOException e) {
            getLogger().log(Level.WARNING, "Failed to save configuration file {0}", configFile);
        }
    }

    private void createDir() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
        }
        catch(Exception e) {
        }
    }

    private void createConfigurations() {

        try {
            File main = new File(getDataFolder(), "main.yml");

            if (!main.exists()) {
                getLogger().log(Level.INFO, "Creating configuration: {0}", main.getName());

                mainConfig = YamlConfiguration.loadConfiguration(main);

                mainConfig.set("punch_world_name", "world_the_end");
                mainConfig.set("guardian_cost_standard", "5000");
                mainConfig.set("guardian_cost_donor", "3000");

                mainConfig.save(main);
            }
            File playerProperties = new File(getDataFolder(), "playerProps.yml");

            if (!playerProperties.exists()) {
                getLogger().log(Level.INFO, "Creating configuration: {0}", playerProperties.getName());

                playerProps = YamlConfiguration.loadConfiguration(playerProperties);
                playerProps.save(playerProperties);
            }
        } catch (IOException e) {
            getLogger().severe("Failed to construct configuration files!");
            getLogger().severe("Sarinsa done goofed, huh? Welp, might as well blame gigo.");
        }
    }
}

package com.sarinsa.core;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.Objects;

public class DAListener implements Listener {


    /**
     * Handles guardian stuff when a player dies.
     */
    @EventHandler
    @SuppressWarnings("all")
    public void guardianDeath(PlayerDeathEvent event) {
        String playerUUID = event.getEntity().getUniqueId().toString();

        if (DecaAddons.PLAYER_PROPS.contains(playerUUID)) {
            if (DecaAddons.PLAYER_PROPS.getConfigurationSection(playerUUID).getBoolean("isGuardianActive")) {
                int guardians = DecaAddons.PLAYER_PROPS.getConfigurationSection(playerUUID).getInt("guardians");

                if (guardians > 0) {
                    Objects.requireNonNull(DecaAddons.PLAYER_PROPS.getConfigurationSection(playerUUID)).set("guardians", --guardians);
                    event.getEntity().sendMessage(ChatColor.GREEN + "You used a guardian to protect your inventory!");
                    event.getEntity().sendMessage(ChatColor.YELLOW + "You now have " + ChatColor.AQUA + guardians + ChatColor.YELLOW + " guardian(s) left.");
                    event.setKeepInventory(true);
                    event.setKeepLevel(true);
                }
                else {
                    event.getEntity().sendMessage(ChatColor.RED + "Oh no! Seems you are out of guardians!");
                }
            }
        }
    }


    /**
     * Prevent fireballs and wither skulls from exploding when summoned by players
     * so that they can be ridden.
     */
    @EventHandler
    public void entityExplodeEvent(ExplosionPrimeEvent event) {
        if (event.getEntity().getType() == EntityType.FIREBALL) {
            Fireball fireball = (Fireball) event.getEntity();

            if (fireball.getShooter() instanceof Player) {
                event.setCancelled(true);
            }
        }
        else if (event.getEntity().getType() == EntityType.WITHER_SKULL) {
            WitherSkull witherSkull = (WitherSkull) event.getEntity();

            if (witherSkull.getShooter() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * Checks if the player used an xp bottle
     * that was created with /xpbottle and
     * acts accordingly.
     */
    @EventHandler
    @SuppressWarnings("all")
    public void onExpBottleConsumed(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();

        if (itemStack == null || !itemStack.hasItemMeta())
            return;

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta.getPersistentDataContainer().isEmpty())
            return;

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (dataContainer.has(NamespacedKey.minecraft("exp_bottle_value"), PersistentDataType.INTEGER)) {
                int exp = dataContainer.getOrDefault(NamespacedKey.minecraft("exp_bottle_value"), PersistentDataType.INTEGER, 0);

                event.getPlayer().giveExp(exp);
                event.getPlayer().getInventory().setItemInMainHand(null);
                event.getPlayer().updateInventory();
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 0.5F);
                event.setCancelled(true);
            }
        }
    }

    /**
     * Removes fireballs and wither skull
     * vehicles when they are dismounted
     */
    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        if (event.getDismounted().getType() == EntityType.WITHER_SKULL || event.getDismounted().getType() == EntityType.FIREBALL) {
            event.getDismounted().remove();
        }
    }

    /**
     * Creates a guardian config for the player
     * logging in if they don't already have one.
     */
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String playerUUID = event.getPlayer().getUniqueId().toString();
        createGuardianConfig(playerUUID);
    }


    /*
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemPickup(PlayerPickupItemEvent event) {

        //if (event.getEntity() instanceof Player) {

            if (DecaAddons.nopickupMap.containsKey(event.getPlayer().getName())) {

                event.setCancelled(true);
            }
        //}
    }
    */

    @SuppressWarnings("all")
    private static void createGuardianConfig(String playerUUID) {
        if (!DecaAddons.PLAYER_PROPS.contains(playerUUID)) {
            DecaAddons.PLAYER_PROPS.createSection(playerUUID);
            DecaAddons.PLAYER_PROPS.getConfigurationSection(playerUUID).set("isGuardianActive", true);
            DecaAddons.PLAYER_PROPS.getConfigurationSection(playerUUID).set("guardians", 0);
            DecaAddons.INSTANCE.saveConfiguration("playerProps.yml", DecaAddons.PLAYER_PROPS);
        }
    }
}

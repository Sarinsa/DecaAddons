package com.sarinsa.util;

import com.sarinsa.core.DecaAddons;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public class Utility {


    public static boolean isGuardianActive(Player player) {
        try {
            return DecaAddons.playerProps.getConfigurationSection(player.getUniqueId().toString()).getBoolean("isGuardianActive");
        }
        catch (Exception e) {
            player.sendMessage(ChatColor.RED + "An error occurred while trying to fetch your active guardian entry.");
            DecaAddons.INSTANCE.getLogger().log(Level.SEVERE, "Failed to retrieve guardian active for player {0}", player.getName());
            return false;
        }
    }


    public static void setGuardianActive(Player player, boolean active) {
        try {
            DecaAddons.playerProps.getConfigurationSection(player.getUniqueId().toString()).set("isGuardianActive", active);
        }
        catch (Exception e) {
            player.sendMessage(ChatColor.RED + "An error occurred while trying to fetch your active guardian entry.");
            DecaAddons.INSTANCE.getLogger().log(Level.SEVERE, "Failed to toggle guardian active for player {0}", player.getName());
        }
    }

    public static NamespacedKey createKey(String key) {
        return new NamespacedKey(DecaAddons.INSTANCE, key);
    }
}

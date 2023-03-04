package com.sarinsa.commands;

import com.sarinsa.util.References;
import com.sarinsa.util.Utility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.server.ServerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class BottleExpExecutor implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(References.PLAYER_ONLY);
            return true;
        }

        if (args.length > 0) {
            player.sendMessage(References.TOO_MANY_ARGS);
            return true;
        }
        if (player.getLevel() <= 0 && player.getExp() <= 0) {
            player.sendMessage(ChatColor.RED + "You do not have any experience to bottle.");
            return true;
        }
        int exp = Utility.getExpFromLevels(player, false);

        if (exp < 0)
            return true;

        ItemStack itemStack = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null)
            return true;

        String s = "'s";
        if (player.getName().endsWith("x") || player.getName().endsWith("z") || player.getName().endsWith("s"))
            s = "'";

        meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + player.getName() + s + " experience");

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.LIGHT_PURPLE + "Experience: " + ChatColor.GREEN + formatExperience(exp));
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(Utility.createKey("bottle_exp_value"), PersistentDataType.INTEGER, exp);
        itemStack.setItemMeta(meta);

        player.setExp(0);
        player.setLevel(0);
        // Update experience on client
        //player.sendExperienceChange(0.0F, 0);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.5f);

        Map<Integer, ItemStack> remains = player.getInventory().addItem(itemStack);

        // Assume the player's inventory was full; drop as an item entity instead.
        if (!remains.isEmpty()) {
            player.getWorld().dropItem(player.getLocation(), itemStack);
        }
        return true;
    }


    private static String formatExperience(int exp) {
        return NumberFormat.getNumberInstance(Locale.US).format(exp);
    }
}

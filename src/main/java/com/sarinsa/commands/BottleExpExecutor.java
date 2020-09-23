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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BottleExpExecutor implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(References.PLAYER_ONLY);
            return true;
        }
        Player player = (Player) sender;

        if (args.length > 0) {
            player.sendMessage(References.TOO_MANY_ARGS);
            return true;
        }

        if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
            player.sendMessage(ChatColor.RED + "Your held itemstack must be empty.");
            player.getServer().getLogger().info(player.getInventory().getItemInMainHand().getType().name());
            return true;
        }
        int exp = player.getTotalExperience();

        if (exp <= 0) {
            player.sendMessage(ChatColor.RED + "You do not have any experience to bottle.");
            return true;
        }

        ItemStack itemStack = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = itemStack.getItemMeta();

        String s = "'s";
        if (player.getName().endsWith("x") || player.getName().endsWith("z") || player.getName().endsWith("s"))
            s = "'";

        meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + player.getName() + s + " experience");

        ArrayList<String> list = new ArrayList<>();
        list.add(ChatColor.LIGHT_PURPLE + "Experience: " + ChatColor.GREEN + formatExperience(exp));
        meta.setLore(list);
        meta.getPersistentDataContainer().set(Utility.createKey("bottle_exp_value"), PersistentDataType.INTEGER, exp);
        itemStack.setItemMeta(meta);

        player.setTotalExperience(0);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.5f);
        player.getInventory().setItemInMainHand(itemStack);
        player.updateInventory();
        return true;
    }

    private static String formatExperience(int exp) {
        return NumberFormat.getNumberInstance(Locale.US).format(exp);
    }
}

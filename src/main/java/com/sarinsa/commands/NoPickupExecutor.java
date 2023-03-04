package com.sarinsa.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.sarinsa.util.References;

public class NoPickupExecutor implements CommandExecutor {


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
        boolean canPickupItems = player.getCanPickupItems();

        if (canPickupItems) {
            player.sendMessage(ChatColor.YELLOW + "NoPickup " + ChatColor.GREEN + "enabled");
        }
        else {
            player.sendMessage(ChatColor.YELLOW + "NoPickup " + ChatColor.RED + "disabled");
        }
        player.setCanPickupItems(!canPickupItems);
        return true;
    }
}

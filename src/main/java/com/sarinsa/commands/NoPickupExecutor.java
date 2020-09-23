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

        if (!(sender instanceof Player)) {
            sender.sendMessage(References.PLAYER_ONLY);
            return true;
        }

        Player player = (Player) sender;

        if (args.length > 0) {
            player.sendMessage(References.TOO_MANY_ARGS);
            return true;
        }
        if (player.getCanPickupItems()) {
            player.setCanPickupItems(false);
            player.sendMessage(ChatColor.YELLOW + "NoPickup " + ChatColor.GREEN + "enabled");
            return true;
        }
        else {
            player.setCanPickupItems(true);
            player.sendMessage(ChatColor.YELLOW + "NoPickup " + ChatColor.RED + "disable");
            return true;
        }
    }
}

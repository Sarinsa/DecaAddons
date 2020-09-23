package com.sarinsa.commands;

import com.sarinsa.util.References;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FakeJoinLeaveExecutor implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(References.PLAYER_ONLY);
            return true;
        }
        Player player = (Player) sender;

        if (cmd.getName().equals("fakejoin")) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /fakejoin (player)");
                return true;
            }
            player.getServer().broadcastMessage(ChatColor.YELLOW + args[0] + " joined the game");
        }
        else {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /fakeleave (player)");
                return true;
            }
            player.getServer().broadcastMessage(ChatColor.YELLOW + args[0] + " left the game");
        }
        return true;
    }
}

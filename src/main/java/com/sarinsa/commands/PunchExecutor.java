package com.sarinsa.commands;

import com.sarinsa.core.DecaAddons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.sarinsa.util.References;

public class PunchExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(References.PLAYER_ONLY);
            return true;
        }

        Player player = (Player) sender;

        if (args.length > 1) {
            player.sendMessage(References.TOO_MANY_ARGS);
            return true;
        }

        Player target = Bukkit.getServer().getPlayer(args[0]);

        if (target != null) {

            String worldName = DecaAddons.mainConfig.getString("punch_world_name");
            if (worldName == null) {
                player.sendMessage(ChatColor.RED + "World variable not set. Nag staff about this; preferably MoNsTeR_WhAt");
                return true;
            }
            if (Bukkit.getServer().getWorld(worldName) == null) {
                player.sendMessage(ChatColor.RED + "Could not find the \"punch world\". Contact staff!");
                return true;
            }

            target.teleport(Bukkit.getServer().getWorld(worldName).getSpawnLocation());
            String name = target.getName();

            if (target.getName().equals(player.getName())) {
                name = "themselves";
            }
            Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + player.getName() + " punched " + name + " into The End!");
            return true;
        }
        else {
            player.sendMessage(ChatColor.RED + "Couldn't find the player.");
            return true;
        }
    }
}

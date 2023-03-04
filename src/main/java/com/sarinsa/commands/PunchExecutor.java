package com.sarinsa.commands;

import com.sarinsa.core.DecaAddons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.sarinsa.util.References;

import java.util.Objects;

public class PunchExecutor implements CommandExecutor {

    // TODO - Sending people to the end is potentially dangerous; consider a special "spawn point"
    //        Also need to create a timer to put a cooldown on the command.
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(References.PLAYER_ONLY);
            return true;
        }
        if (args.length > 1) {
            player.sendMessage(References.TOO_MANY_ARGS);
            return true;
        }
        if (args[0] == null || args[0].isEmpty()) {
            player.sendMessage(ChatColor.RED + "You must specify a user to punch!");
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);

        if (target != null) {
            String worldName = DecaAddons.CONFIG.getString("punch_world_name");
            String locString = DecaAddons.CONFIG.getString("punch_location");
            String worldName = DecaAddons.CONFIG.getString("punch_world_name");
            if (worldName == null) {
                player.sendMessage(ChatColor.RED + "World variable not set. Nag staff about this; preferably MoNsTeR_WhAt");
                return true;
            }
            if (Bukkit.getServer().getWorld(worldName) == null) {
                player.sendMessage(ChatColor.RED + "Could not find the \"punch world\". Contact staff!");
                return true;
            }
            target.teleport(Objects.requireNonNull(Bukkit.getServer().getWorld(worldName)).getSpawnLocation());
            String name = target.getName();

            if (target.getName().equals(player.getName())) {
                name = "themselves";
            }
            Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + player.getName() + " punched " + name + " into The End!");
        }
        else {
            player.sendMessage(ChatColor.RED + "Couldn't find the player.");
        }
        return true;
    }
}

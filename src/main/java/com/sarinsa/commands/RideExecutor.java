package com.sarinsa.commands;

import com.sarinsa.util.References;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class RideExecutor implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(References.PLAYER_ONLY);
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1) {

            List<Entity> entityList = player.getNearbyEntities(5, 5, 5);

            if (entityList.isEmpty()) {

                player.sendMessage(ChatColor.RED + "Player is not within reach!");
                return true;
            }
            else {
                for (Entity entity : entityList) {
                    if (entity.getName().equals(args[0]) && entity instanceof Player) {
                        Player target = (Player) entity;
                        if (!target.getPassengers().isEmpty()) {
                            player.sendMessage(ChatColor.RED + target.getName() + " already has a passenger!");
                            return true;
                        }
                        target.addPassenger(player);
                        player.sendMessage(ChatColor.YELLOW + "You are now riding " + ChatColor.GOLD + target.getName() + ChatColor.YELLOW + "!");
                        return true;
                    }
                }
            }
        }
        else {
            List<Entity> entities = player.getNearbyEntities(1, 1, 1);

            if (entities.isEmpty()) {
                player.sendMessage(ChatColor.RED + "No nearby entities to ride.");
                return true;
            }
            else {
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity) {
                        entity.addPassenger(player);
                        player.sendMessage(ChatColor.YELLOW + "Whoosh!");
                        return true;
                    }
                    return true;
                }
            }
        }
        return false;
    }
}

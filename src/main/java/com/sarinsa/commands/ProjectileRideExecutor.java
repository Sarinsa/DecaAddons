package com.sarinsa.commands;

import com.sarinsa.util.References;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;

public class ProjectileRideExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(References.PLAYER_ONLY);
            return true;
        }

        if (args.length > 0) {
            sender.sendMessage(References.TOO_MANY_ARGS);
            return true;
        }

        Player player = (Player) sender;
        Location location = player.getLocation();
        location.setY(location.getY() + 0.3);

        if (label.equals("fireride")) {
            // Do this so the fireball doesn't collide with anything right away
            location.setX(location.getX() + 0.5);

            Fireball fireball = (Fireball) player.getWorld().spawnEntity(location, EntityType.FIREBALL);
            fireball.setShooter(player);
            fireball.setBounce(true);
            fireball.addPassenger(player);

            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1f, 1f);
            return true;
        }
        else if (label.equals("skullride")) {
            WitherSkull witherSkull = (WitherSkull) player.getWorld().spawnEntity(location, EntityType.WITHER_SKULL);
            witherSkull.setShooter(player);
            witherSkull.setCharged(true);
            witherSkull.setBounce(true);
            witherSkull.addPassenger(player);

            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1f, 1f);
            return true;
        }
        return true;
    }
}

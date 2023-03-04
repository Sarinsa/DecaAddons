package com.sarinsa.commands;

import com.sarinsa.core.DecaAddons;
import com.sarinsa.util.References;
import com.sarinsa.util.Utility;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class GuardiansExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        int guardians = Objects.requireNonNull(DecaAddons.PLAYER_PROPS.getConfigurationSection(player.getUniqueId().toString())).getInt("guardians");

        String status = ChatColor.RED + "OFF";

        if (Utility.isGuardianActive(player)) {
            status = ChatColor.GREEN + "ON";
        }
        if (args.length < 1) {
            player.sendMessage(ChatColor.AQUA + "You currently have " + ChatColor.GREEN + guardians + ChatColor.AQUA + " Guardians. " + ChatColor.GRAY + "(" + ChatColor.YELLOW + "Guardians are " + status + ChatColor.GRAY + ")");
            player.sendMessage(ChatColor.GRAY + "Pssst. Check out the list of guardian related commands with");
            player.sendMessage(ChatColor.AQUA + "/guardians help");
        }
        else {
            if (args[0].equalsIgnoreCase("buy")) {
                double money = DecaAddons.economy.getBalance(player);
                double cost = player.hasPermission(References.DONOR_PERM)
                        ? DecaAddons.guardianDonorCost
                        : DecaAddons.guardianBaseCost;

                //Buying 1
                if (args.length == 1) {
                    if (money >= cost) {
                        DecaAddons.economy.withdrawPlayer(player, cost);
                        Objects.requireNonNull(DecaAddons.PLAYER_PROPS.getConfigurationSection(player.getUniqueId().toString())).set("guardians", ++guardians);
                        DecaAddons.INSTANCE.saveConfiguration("playerProps.yml", DecaAddons.PLAYER_PROPS);

                        player.sendMessage(ChatColor.GREEN + "You purchased a Guardian.");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "You don't have sufficient funds " + ChatColor.BLUE + "(" + ChatColor.AQUA + DecaAddons.economy.format(cost) + ChatColor.BLUE + ")");
                    }
                    return true;
                }

                //Buying multiple
                else if (args.length == 2) {
                    int amount;

                    try {
                        amount = Integer.parseInt(args[1]);
                    }
                    catch (NumberFormatException e) {
                        e.printStackTrace();
                        player.sendMessage(ChatColor.RED + "\"" + args[1] + "\" is not a valid number.");
                        return true;
                    }
                    if (amount <= 0) {
                        player.sendMessage(ChatColor.RED + "Amount must be a positive number!");
                        return true;
                    }
                    cost *= amount;

                    if (money >= cost) {
                        DecaAddons.economy.withdrawPlayer(player, cost);
                        Objects.requireNonNull(DecaAddons.PLAYER_PROPS.getConfigurationSection(player.getUniqueId().toString())).set("guardians", guardians + amount);
                        DecaAddons.INSTANCE.saveConfiguration("playerProps.yml", DecaAddons.PLAYER_PROPS);

                        player.sendMessage(ChatColor.GREEN + "You purchased " + ChatColor.AQUA + amount + ChatColor.GREEN + " guardians");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "You don't have sufficient funds " + ChatColor.BLUE + "(" + ChatColor.AQUA + DecaAddons.economy.format(cost) + ChatColor.BLUE + ")");
                    }
                    return true;
                }
                else {
                    player.sendMessage(References.TOO_MANY_ARGS);
                    return true;
                }
            }
            else if (args[0].equalsIgnoreCase("help")) {
                player.sendMessage(ChatColor.GRAY + "--------------------------------------------");
                player.sendMessage(References.GUARDIAN_HELP);
                player.sendMessage(ChatColor.GRAY + "--------------------------------------------");
            }
            else if (args[0].equalsIgnoreCase("toggle")) {

                if (Utility.isGuardianActive(player)) {
                    Utility.setGuardianActive(player, false);
                    player.sendMessage(ChatColor.YELLOW + "Toggled Guardians " + ChatColor.RED + "" + ChatColor.BOLD + "OFF");
                }
                else {
                    Utility.setGuardianActive(player, true);
                    player.sendMessage(ChatColor.YELLOW + "Toggled Guardians " + ChatColor.GREEN + "" + ChatColor.BOLD + "ON");
                }
                return true;
            }
            else {
                player.sendMessage(ChatColor.RED + "Invalid subcommand. Type /guardians help for a list of commands");
                return true;
            }
        }
        return true;
    }
}

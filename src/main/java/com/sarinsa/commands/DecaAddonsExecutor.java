package com.sarinsa.commands;

import com.sarinsa.core.DecaAddons;
import com.sarinsa.util.References;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.Map;

public class DecaAddonsExecutor implements CommandExecutor {

    private final String FANCY_1 = ChatColor.GRAY + "----------[" + ChatColor.AQUA + "" + ChatColor.ITALIC + "DecaAddons" + ChatColor.GRAY + "]----------";
    private final String FANCY_2 = ChatColor.GRAY + "--------------------------------";

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        PluginDescriptionFile descFile = DecaAddons.INSTANCE.getDescription();

        if (args.length < 1) {
            sender.sendMessage(FANCY_1);
            sender.sendMessage(ChatColor.GREEN + "Plugin version: " + ChatColor.YELLOW + descFile.getVersion());
            sender.sendMessage(ChatColor.GREEN + "API version: " + ChatColor.YELLOW + descFile.getAPIVersion());
            sender.sendMessage(ChatColor.GREEN + "Author: " + ChatColor.YELLOW + descFile.getAuthors());
            sender.sendMessage(ChatColor.GREEN + "Dependencies: " + ChatColor.YELLOW + descFile.getDepend());
            sender.sendMessage(FANCY_2);
            return true;
        }
        if (args.length == 1) {
            Map<String, Map<String, Object>> commandMap = descFile.getCommands();

            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(FANCY_1);

                for (Map<String, Object> map : commandMap.values()) {
                    sender.sendMessage(ChatColor.AQUA + (String)map.get("usage") + ChatColor.GRAY + " - " + ChatColor.YELLOW + map.get("description"));
                }
                sender.sendMessage(FANCY_2);
                return true;
            }
            else if (args[0].equalsIgnoreCase("permissions")) {
                if (sender.hasPermission("decaaddons.permissions")) {

                    String[] labels = commandMap.keySet().toArray(new String[0]);
                    int index = 0;

                    sender.sendMessage(ChatColor.GREEN + "Commands and permissions associated with DecaAddons:");

                    for (Map<String, Object> map : commandMap.values()) {
                        String permission = ChatColor.YELLOW + (String)map.get("permission");

                        if (map.get("permission") == null || map.get("permission").equals("null"))

                            permission = ChatColor.GRAY + "No permission";
                        sender.sendMessage(ChatColor.AQUA + "/" + labels[index] + ChatColor.GRAY + " - " + ChatColor.YELLOW + permission);
                        index += 1;
                    }
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Nay.");
                return true;
            }
            else {
                sender.sendMessage(ChatColor.RED + "Invalid subcommand. Type \"/da help\" for a list of commands");
                return true;
            }
        }
        else {
            sender.sendMessage(References.TOO_MANY_ARGS);
            return true;
        }
    }
}

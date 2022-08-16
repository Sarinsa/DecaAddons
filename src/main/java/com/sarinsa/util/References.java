package com.sarinsa.util;

import org.bukkit.ChatColor;

public class References {

    // PERMISSIONS
    public static final String DONOR_PERM = "decaaddons.guardians.donor";



    // MESSAGES
    public static String PLAYER_ONLY = "This command can only be issued by a player!";
    public static String TOO_MANY_ARGS  = ChatColor.RED + "Too many arguments!";

    public static final String[] GUARDIAN_HELP = {
            ChatColor.GREEN + "/guardians " + ChatColor.AQUA + "- See how many guardians you have left.",
            ChatColor.GREEN + "/guardians help " + ChatColor.AQUA + "- Displays this message.",
            ChatColor.GREEN + "/guardians buy [amount]|max " + ChatColor.AQUA + "- Purchase one or more guardians.",
            ChatColor.GREEN + "/guardians toggle " + ChatColor.AQUA + "- Toggles guardians on or off.",
    };
}

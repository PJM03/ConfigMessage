package com.github.pjm03.messageutil;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class Utility {
    public static boolean nullCheck(Object... o) {
        for (Object s : o) {
            if (s == null) return true;
        }
        return false;
    }

    public static String prepareMessage(Player p, String string) {
        if (string == null) return null;
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}

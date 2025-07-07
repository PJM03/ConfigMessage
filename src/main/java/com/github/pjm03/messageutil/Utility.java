package com.github.pjm03.messageutil;

import me.clip.placeholderapi.PlaceholderAPI;
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
        string = ChatColor.translateAlternateColorCodes('&', string);
        return PlaceholderAPI.setPlaceholders(p, string);
    }
}

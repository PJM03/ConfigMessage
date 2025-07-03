package com.github.pjm03.configmessage;

import net.md_5.bungee.api.ChatColor;

public class ConfigMessageUtil {
    public static boolean nullCheck(Object... o) {
        for (Object s : o) {
            if (s == null) return true;
        }
        return false;
    }

    public static String colorString(String string) {
        return string == null ? null : ChatColor.translateAlternateColorCodes('&', string);
    }
}

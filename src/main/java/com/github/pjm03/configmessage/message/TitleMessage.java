package com.github.pjm03.configmessage.message;

import com.github.pjm03.configmessage.ConfigMessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TitleMessage extends Message {
    public TitleMessage(Plugin plugin, String path) {
        super(plugin, path);
    }

    @Override
    public void send(Player p) {
        String title = ConfigMessageUtil.colorString(plugin.getConfig().getString(path + ".title"));
        String subtitle = ConfigMessageUtil.colorString(plugin.getConfig().getString(path + ".subtitle"));
        int fadeIn = plugin.getConfig().getInt(path + ".fadein");
        int stay = plugin.getConfig().getInt(path + ".stay");
        int fadeOut = plugin.getConfig().getInt(path + ".fadeout");

        if(ConfigMessageUtil.nullCheck(title, subtitle, fadeIn, stay, fadeOut)) {
            throw new IllegalArgumentException("[title, subtitle, fadeIn, stay, fadeOut] cannot be null. Please check config file");
        }

        p.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }
}
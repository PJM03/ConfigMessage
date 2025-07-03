package com.github.pjm03.configmessage.message;

import com.github.pjm03.configmessage.ConfigMessageUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ActionBarMessage extends Message {
    public ActionBarMessage(Plugin plugin, String path) {
        super(plugin, path);
    }

    @Override
    public void send(Player p) {
        String text = ConfigMessageUtil.colorString(this.path + ".message");
        if (ConfigMessageUtil.nullCheck(text)) throw new IllegalArgumentException("Message cannot be null");

        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(text));
    }
}

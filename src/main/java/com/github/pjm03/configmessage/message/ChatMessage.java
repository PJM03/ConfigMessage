package com.github.pjm03.configmessage.message;

import com.github.pjm03.configmessage.ConfigMessageUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ChatMessage extends Message {
    public ChatMessage(Plugin plugin, String path) {
        super(plugin, path);
    }

    @Override
    public void send(Player p) {
        FileConfiguration config = this.plugin.getConfig();
        List<String> stringList = config.getStringList(this.path + ".message");
        if (stringList.isEmpty()) throw new IllegalArgumentException("Message cannot be null");

        List<String> message = stringList.stream()
                .map(line -> PlaceholderAPI.setPlaceholders(p, ConfigMessageUtil.colorString(line)))
                .toList();
        message.forEach(p::sendMessage);
    }
}

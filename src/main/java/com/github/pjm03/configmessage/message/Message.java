package com.github.pjm03.configmessage.message;

import com.github.pjm03.configmessage.ConfigMessageUtil;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@RequiredArgsConstructor
public class Message {
    protected final Plugin plugin;
    protected final String path;

    public void send(Player p) {
        ConfigurationSection section = this.plugin.getConfig().getConfigurationSection(path);
        if (section == null) throw new IllegalArgumentException("'" + path + "' does not exist");

        switch (section.getString("type", "null").toUpperCase()) {
            case "CHAT" -> sendChat(p, section.getStringList("message"));
            case "ACTION_BAR", "ACTIONBAR" -> sendActionBar(p, section.getString("message"));
            case "TITLE" -> sendTitle(
                    p,
                    section.getString("title"),
                    section.getString("subtitle"),
                    section.getInt("fade-in"),
                    section.getInt("stay"),
                    section.getInt("fade-out")
            );
            default -> throw new IllegalArgumentException("Unsupported message type: " + section.getString("type"));
        }
    };

    private void sendChat(Player p, @NotNull List<String> message) {
        if (message.isEmpty()) throw new IllegalArgumentException("Message cannot be null");

        message.stream()
                .map(line -> ConfigMessageUtil.prepareMessage(p, line))
                .forEach(p::sendMessage);
    }

    private void sendActionBar(Player p, String message) {
        if (ConfigMessageUtil.nullCheck(message)) throw new IllegalArgumentException("Message cannot be null");
        message = ConfigMessageUtil.prepareMessage(p, message);
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    private void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        title = ConfigMessageUtil.prepareMessage(p, title);
        subtitle = ConfigMessageUtil.prepareMessage(p, subtitle);

        if(ConfigMessageUtil.nullCheck(title, subtitle, fadeIn, stay, fadeOut)) {
            throw new IllegalArgumentException("[title, subtitle, fadeIn, stay, fadeOut] cannot be null. Please check config file");
        }

        if (fadeIn <= 0 ||  stay <= 0 || fadeOut <= 0) {
            throw new IllegalArgumentException("[fade-in, stay, fade-out] cannot be negative");
        }

        p.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }
}

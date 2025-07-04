package com.github.pjm03.configmessage.config;

import com.github.pjm03.configmessage.message.ActionBar;
import com.github.pjm03.configmessage.message.Chat;
import com.github.pjm03.configmessage.message.Title;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public class PluginConfig implements Config {
    private final Plugin plugin;
    private final String path;

    public Message parseMessage(String key) {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection(this.path);
        if (section == null) throw new IllegalArgumentException("'" + this.path + "' does not exist");
        if (!section.isSet(key)) throw new IllegalArgumentException("'" + key + "' does not exist");

        String type = section.getString(key + ".type");
        if (type == null) throw new IllegalArgumentException("'" + key + ".type' does not exist");

        return switch (type.toUpperCase()) {
            case "CHAT" -> new Message(() -> new Chat(section.getStringList(key + ".message")));
            case "ACTIONBAR" -> new Message(() -> new ActionBar(section.getString(key + ".message")));
            case "TITLE" -> new Message(() -> new Title(
                    section.getString(key + ".title"),
                    section.getString(key + ".subtitle"),
                    section.getInt(key + "fade-in"),
                    section.getInt(key + "stay"),
                    section.getInt(key + "fade-out")
            ));

            default -> throw new IllegalArgumentException("type '" + type + "' is unsupported.");
        };
    }

    @Override
    public void reload() {
        plugin.reloadConfig();
    }
}

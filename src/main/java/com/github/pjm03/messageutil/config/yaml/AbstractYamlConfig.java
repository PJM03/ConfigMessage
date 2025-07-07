package com.github.pjm03.messageutil.config.yaml;

import com.github.pjm03.messageutil.config.Config;
import com.github.pjm03.messageutil.message.ConfigMessage;
import com.github.pjm03.messageutil.message.ActionBar;
import com.github.pjm03.messageutil.message.Chat;
import com.github.pjm03.messageutil.message.Title;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;

@RequiredArgsConstructor
public abstract class AbstractYamlConfig implements Config {
    private final String path;

    @Override
    public ConfigMessage parseMessage(String key) {
        ConfigurationSection section = getConfigurationSection(this.path);
        if (section == null) throw new IllegalArgumentException("'" + this.path + "' does not exist");
        if (!section.isSet(key)) throw new IllegalArgumentException("'" + key + "' does not exist");

        String type = section.getString(key + ".type");
        if (type == null) throw new IllegalArgumentException("'" + key + ".type' does not exist");

        return switch (type.toUpperCase()) {
            case "CHAT" -> new ConfigMessage(() -> new Chat(section.getStringList(key + ".message")));
            case "ACTIONBAR" -> new ConfigMessage(() -> new ActionBar(section.getString(key + ".message")));
            case "TITLE" -> new ConfigMessage(() -> new Title(
                    section.getString(key + ".title"),
                    section.getString(key + ".subtitle"),
                    section.getInt(key + "fade-in"),
                    section.getInt(key + "stay"),
                    section.getInt(key + "fade-out")
            ));

            default -> throw new IllegalArgumentException("type '" + type + "' is unsupported.");
        };
    }

    protected abstract ConfigurationSection getConfigurationSection(String path);
}

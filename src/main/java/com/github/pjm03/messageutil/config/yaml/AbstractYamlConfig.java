package com.github.pjm03.messageutil.config.yaml;

import com.github.pjm03.messageutil.config.Config;
import com.github.pjm03.messageutil.message.ConfigMessage;
import com.github.pjm03.messageutil.message.ActionBar;
import com.github.pjm03.messageutil.message.Chat;
import com.github.pjm03.messageutil.message.Title;
import com.github.pjm03.messageutil.message.chain.MessageChain;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractYamlConfig implements Config {
    private final String messagePath;
    private final String chainPath;

    @Override
    public List<ConfigMessage> parseAllMessages() {
        ConfigurationSection section = getConfigurationSection(this.messagePath);
        if (section == null) throw new IllegalArgumentException("'" + this.messagePath + "' does not exist");

        return section.getKeys(false).stream()
                .map(this::parseMessage)
                .toList();
    }

    @Override
    public ConfigMessage parseMessage(String key) {
        ConfigurationSection section = getConfigurationSection(this.messagePath);
        if (section == null) throw new IllegalArgumentException("'" + this.messagePath + "' does not exist");
        if (!section.isSet(key)) throw new IllegalArgumentException("'" + key + "' does not exist in message section");

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

    @Override
    public List<MessageChain> parseAllMessageChains() {
        ConfigurationSection section = getConfigurationSection(this.chainPath);
        if (section == null) throw new IllegalArgumentException("'" + this.chainPath + "' does not exist");

        return section.getKeys(false).stream()
                .map(this::parseChain)
                .toList();
    }

    @Override
    public MessageChain parseChain(String key) {
        ConfigurationSection section = getConfigurationSection(this.chainPath);
        if (section == null) throw new IllegalArgumentException("'" + this.chainPath + "' does not exist");
        if (!section.isSet(key)) throw new IllegalArgumentException("'" + key + "' does not exist in chain section");
        if (!section.isSet(key + ".messages")) throw new IllegalArgumentException("'messages' does not exist in chain '" + key + "'");

        boolean repeat = section.getBoolean(key + ".repeat", false);
        List<MessageChain.Message> messages = section.getMapList(key + ".messages").stream()
                .map(m -> {
                    return new MessageChain.Message(parseMessage(m.get("name").toString()), (int) m.get("delay"));
                })
                .toList();

        return new MessageChain(messages, repeat);
    }

    protected abstract ConfigurationSection getConfigurationSection(String path);
}

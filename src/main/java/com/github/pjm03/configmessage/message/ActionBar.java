package com.github.pjm03.configmessage.message;

import com.github.pjm03.configmessage.ConfigMessageUtil;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class ActionBar implements AbstractMessage {
    private final String message;

    @Override
    public void send(Player p) {
        if (ConfigMessageUtil.nullCheck(message)) throw new IllegalArgumentException("Message cannot be null");
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ConfigMessageUtil.prepareMessage(p, message)));
    }
}

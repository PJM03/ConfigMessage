package com.github.pjm03.messageutil.message;

import com.github.pjm03.messageutil.Utility;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class ActionBar implements AbstractMessage {
    private final String message;

    @Override
    public void send(Player p) {
        if (Utility.nullCheck(message)) throw new IllegalArgumentException("Message cannot be null");
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utility.prepareMessage(p, message)));
    }
}

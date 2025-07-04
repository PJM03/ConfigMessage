package com.github.pjm03.configmessage.message;

import com.github.pjm03.configmessage.ConfigMessageUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredArgsConstructor
public class Chat implements AbstractMessage {
    private final List<String> message;

    @Override
    public void send(Player p) {
        if (message.isEmpty()) throw new IllegalArgumentException("Message cannot be null");

        message.stream()
                .map(line -> ConfigMessageUtil.prepareMessage(p, line))
                .forEach(p::sendMessage);
    }
}

package com.github.pjm03.messageutil.message;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class ConfigMessage implements AbstractMessage {
    private final Supplier<? extends AbstractMessage> supplier;

    public void send(Player p) {
        supplier.get().send(p);
    }

    @Override
    public String toString() {
        return supplier.get().toString();
    }
}

package com.github.pjm03.configmessage.message;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public abstract class Message {
    protected final Plugin plugin;
    protected final String path;

    public abstract void send(Player p);
}

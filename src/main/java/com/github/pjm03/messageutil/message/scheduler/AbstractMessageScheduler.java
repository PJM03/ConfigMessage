package com.github.pjm03.messageutil.message.scheduler;

import com.github.pjm03.messageutil.message.chain.MessageChain;
import org.bukkit.entity.Player;

public interface AbstractMessageScheduler<T> {
    T send(Player p, MessageChain chain);
}

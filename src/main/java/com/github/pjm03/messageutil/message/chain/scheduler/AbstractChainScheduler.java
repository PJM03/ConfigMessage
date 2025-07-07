package com.github.pjm03.messageutil.message.chain.scheduler;

import com.github.pjm03.messageutil.message.chain.MessageChain;
import org.bukkit.entity.Player;

public interface AbstractChainScheduler<T> {
    T send(Player p, MessageChain chain);
}

package com.github.pjm03.messageutil.message.chain.scheduler;

import com.github.pjm03.messageutil.message.chain.MessageChain;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@RequiredArgsConstructor
public class BukkitChainScheduler implements AbstractChainScheduler<BukkitTask> {
    private final Plugin plugin;

    @Override
    public BukkitTask send(Player p, MessageChain chain) {
        return new MessageChainTask(this.plugin, chain, p).runTask(this.plugin);
    }

    @RequiredArgsConstructor
    private static class MessageChainTask extends BukkitRunnable {
        private final Plugin plugin;
        private final MessageChain chain;
        private final Player p;
        private int index = 0;

        @Override
        public void run() {
            if (this.index >= this.chain.messages().size()) {
                if (this.chain.repeat()) {
                    this.index = 0;
                } else return;
            }

            MessageChain.Message message = this.chain.messages().get(this.index);
            message.message().send(this.p);
            System.out.println("send! = " + message.message());
            this.runTaskLater(this.plugin, message.delay());
        }
    }
}

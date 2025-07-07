package com.github.pjm03.messageutil.message;

import com.github.pjm03.messageutil.Utility;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class Title implements AbstractMessage {
    private final String title;
    private final String subtitle;
    private final int fadeIn;
    private final int stay;
    private final int fadeOut;

    @Override
    public void send(Player p) {
        String title = Utility.prepareMessage(p, this.title);
        String subtitle = Utility.prepareMessage(p, this.subtitle);

        if(Utility.nullCheck(title, subtitle)) {
            throw new IllegalArgumentException("[title, subtitle, fadeIn, stay, fadeOut] cannot be null. Please check config file");
        }

        if (fadeIn <= 0 ||  stay <= 0 || fadeOut <= 0) {
            throw new IllegalArgumentException("[fade-in, stay, fade-out] cannot be negative");
        }

        p.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }
}

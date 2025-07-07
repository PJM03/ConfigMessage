package com.github.pjm03.messageutil.message.chain;

import com.github.pjm03.messageutil.message.AbstractMessage;

import java.util.List;

public record MessageChain(
        List<Message> messages,
        boolean repeat
) {

    public record Message(
            AbstractMessage message,
            int delay
    ) {}

}

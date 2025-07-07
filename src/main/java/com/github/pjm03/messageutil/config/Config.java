package com.github.pjm03.messageutil.config;

import com.github.pjm03.messageutil.message.ConfigMessage;
import com.github.pjm03.messageutil.message.chain.MessageChain;

import java.io.IOException;
import java.util.List;

public interface Config {
    List<ConfigMessage> parseAllMessages();
    ConfigMessage parseMessage(String key);

    List<MessageChain> parseAllMessageChains();
    MessageChain parseChain(String key);
    void reload() throws IOException;
}

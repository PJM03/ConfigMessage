package com.github.pjm03.messageutil.config;

import com.github.pjm03.messageutil.message.ConfigMessage;

import java.io.IOException;

public interface Config {
    ConfigMessage parseMessage(String key);
    void reload() throws IOException;
}

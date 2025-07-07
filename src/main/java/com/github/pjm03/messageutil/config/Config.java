package com.github.pjm03.messageutil.config;

import com.github.pjm03.messageutil.message.Message;

public interface Config {
    Message parseMessage(String key);
    void reload();
}

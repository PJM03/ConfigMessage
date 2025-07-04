package com.github.pjm03.configmessage.config;

public interface Config {
    Message parseMessage(String key);
    void reload();
}

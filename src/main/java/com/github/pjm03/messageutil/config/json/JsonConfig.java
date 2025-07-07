package com.github.pjm03.messageutil.config.json;

import com.github.pjm03.messageutil.config.Config;
import com.github.pjm03.messageutil.message.ConfigMessage;
import com.github.pjm03.messageutil.message.ActionBar;
import com.github.pjm03.messageutil.message.Chat;
import com.github.pjm03.messageutil.message.Title;
import com.github.pjm03.messageutil.message.chain.MessageChain;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;

public class JsonConfig implements Config {
    private static final Gson gson = new Gson();

    private final File file;
    private final String messagePath;
    private final String chainPath;
    private JsonObject json;

    public JsonConfig(File file, String messagePath, String chainPath) throws IOException {
        this.messagePath = messagePath;
        this.chainPath = chainPath;
        this.file = file;

        try(Reader reader = Files.newBufferedReader(file.toPath())) {
            this.json = gson.fromJson(reader, JsonObject.class);
        }
    }

    @Override
    public List<ConfigMessage> parseAllMessages() {
        JsonObject messageSection = getValue(this.messagePath, json::getAsJsonObject, p -> "path '" + p + "' does not exist");
        return messageSection.keySet().stream()
                .map(this::parseMessage)
                .toList();
    }

    @Override
    public ConfigMessage parseMessage(String key) {
        JsonObject messageSection = getValue(this.messagePath, json::getAsJsonObject, p -> "path '" + p + "' does not exist");

        JsonObject message = getValue(key, messageSection::getAsJsonObject, k -> "message '" + k + "' does not exist");

        String type = getValue("type", message::get, k -> "required key '" + k + "' does not exist in message block")
                .getAsString();

        switch (type.toUpperCase()) {
            case "CHAT" -> {
                return new ConfigMessage(() -> new Chat(
                        getValue("message", message::getAsJsonArray, k -> "required key '" + k + "' does not exist in message block")
                                .asList().stream()
                                .map(JsonElement::getAsString)
                                .toList()
                ));
            }
            case "ACTIONBAR" -> {
                return new ConfigMessage(() -> new ActionBar(
                        getValue("message", message::get, k -> "required key '" + k + "' does not exist in message block")
                                .getAsString()));
            }

            case "TITLE" -> {
                Function<String, String> emc = k -> "required key '" + k + "' does not exist in message block";
                return new ConfigMessage(() -> new Title(
                        getValue("title", message::get, emc).getAsString(),
                        getValue("subtitle", message::get, emc).getAsString(),
                        getValue("fade-in", message::get, emc).getAsInt(),
                        getValue("stay", message::get, emc).getAsInt(),
                        getValue("fade-out", message::get, emc).getAsInt()

                ));
            }

            default -> throw new IllegalArgumentException("type '" + type + "' is unsupported.");
        }
    }

    @Override
    public List<MessageChain> parseAllMessageChains() {
        JsonObject chainSection = getValue(this.chainPath, json::getAsJsonObject, p -> "path '" + p + "' does not exist");

        return chainSection.keySet().stream()
                .map(this::parseChain)
                .toList();
    }

    @Override
    public MessageChain parseChain(String key) {
        JsonObject chainSection = getValue(this.chainPath, json::getAsJsonObject, p -> "path '" + p + "' does not exist");

        JsonObject chain = getValue(key, chainSection::getAsJsonObject, k -> "chain '" + k + "' does not exist");

        boolean repeat = getValueOrDefault("repeat", k -> chain.get(k).getAsBoolean(), false);
        List<MessageChain.Message> messages = getValue("messages", chain::getAsJsonArray, k -> "required key '" + k + "' does not exist in chain block")
                .asList().stream()
                .map(JsonElement::getAsJsonObject)
                .map(o -> new MessageChain.Message(parseMessage(o.get("name").getAsString()), o.get("delay").getAsInt()))
                .toList();

        return new MessageChain(messages, repeat);
    }

    @Override
    public void reload() throws IOException {
        try(Reader reader = Files.newBufferedReader(file.toPath())) {
            this.json = gson.fromJson(reader, JsonObject.class);
        }
    }

    private <T> T getValue(String key, Function<String, T> getter, Function<String, String> errorMessageConsumer) {
        T value = getter.apply(key);
        if (value == null) {
            throw new IllegalArgumentException(errorMessageConsumer.apply(key));
        }
        return value;
    }

    private <T> T getValueOrDefault(String key, Function<String, T> getter, T def) {
        T value = getter.apply(key);
        return value == null ? def : value;
    }
}

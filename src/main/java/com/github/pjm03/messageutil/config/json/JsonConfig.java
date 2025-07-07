package com.github.pjm03.messageutil.config.json;

import com.github.pjm03.messageutil.config.Config;
import com.github.pjm03.messageutil.message.Message;
import com.github.pjm03.messageutil.message.ActionBar;
import com.github.pjm03.messageutil.message.Chat;
import com.github.pjm03.messageutil.message.Title;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.function.Function;

public class JsonConfig implements Config {
    private static final Gson gson = new Gson();

    private final File file;
    private final String path;
    private JsonObject json;

    public JsonConfig(File file, String path) throws IOException {
        this.path = path;
        this.file = file;

        try(Reader reader = Files.newBufferedReader(file.toPath())) {
            this.json = gson.fromJson(reader, JsonObject.class);
        }
    }

    @Override
    public Message parseMessage(String key) {
        JsonObject messageSection = getValue(this.path, json::getAsJsonObject, p -> "path '" + p + "' does not exist");

        JsonObject message = getValue(key, messageSection::getAsJsonObject, k -> "message '" + k + "' does not exist");

        String type = getValue("type", message::get, k -> "required key '" + k + "' does not exist in message block")
                .getAsString();

        switch (type.toUpperCase()) {
            case "CHAT" -> {
                return new Message(() -> new Chat(
                        getValue("message", message::getAsJsonArray, k -> "required key '" + k + "' does not exist in message block")
                                .asList().stream()
                                .map(JsonElement::getAsString)
                                .toList()
                ));
            }
            case "ACTIONBAR" -> {
                return new Message(() -> new ActionBar(
                        getValue("message", message::get, k -> "required key '" + k + "' does not exist in message block")
                                .getAsString()));
            }

            case "TITLE" -> {
                Function<String, String> emc = k -> "required key '" + k + "' does not exist in message block";
                return new Message(() -> new Title(
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
    public void reload() {
        try(Reader reader = Files.newBufferedReader(file.toPath())) {
            this.json = gson.fromJson(reader, JsonObject.class);
        }  catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private <T> T getValue(String key, Function<String, T> getter, Function<String, String> errorMessageConsumer) {
        T value = getter.apply(key);
        if (value == null) {
            throw new IllegalArgumentException(errorMessageConsumer.apply(key));
        }
        return value;
    }
}

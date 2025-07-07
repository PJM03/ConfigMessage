package com.github.pjm03.messageutil.config.yaml;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class YamlConfig extends AbstractYamlConfig {
    private final File file;

    private FileConfiguration config;

    public YamlConfig(File file, String path) {
        super(path);
        this.file = file;

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    protected ConfigurationSection getConfigurationSection(String path) {
        if (path == null || path.isBlank()) return this.config;

        return this.config.getConfigurationSection(path);
    }

    @Override
    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }
}

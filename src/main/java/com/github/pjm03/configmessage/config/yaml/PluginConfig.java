package com.github.pjm03.configmessage.config.yaml;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

public class PluginConfig extends AbstractYamlConfig {
    private final Plugin plugin;

    public PluginConfig(Plugin plugin, String path) {
        super(path);
        this.plugin = plugin;
    }

    @Override
    protected ConfigurationSection getConfigurationSection(String path) {
        if (path == null || path.isBlank()) return this.plugin.getConfig();

        return this.plugin.getConfig().getConfigurationSection(path);
    }

    @Override
    public void reload() {
        plugin.reloadConfig();
    }
}

package com.raptorbk.CyanWarriorSwordsRedux.config;

public class ConfigProperty {
    private final String key;


    private final ConfigValue value;

    public ConfigProperty(String key, ConfigValue value) {
        this.key = key;
        this.value = value;
    }

    public ConfigProperty(String key) {
        this.key = key;
        this.value = ConfigValueType.TRUE;
    }

    public String key() {
        return key;
    }

    public ConfigValue value() {
        return value;
    }
}
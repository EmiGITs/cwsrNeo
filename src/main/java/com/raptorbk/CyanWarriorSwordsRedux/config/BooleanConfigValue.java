package com.raptorbk.CyanWarriorSwordsRedux.config;

public class BooleanConfigValue implements ConfigValue {
    Boolean value;

    public BooleanConfigValue(Boolean value) {
        this.value = value;
    }

    @Override
    public ConfigValueType getType() {
        return ConfigValueType.BOOLEAN;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
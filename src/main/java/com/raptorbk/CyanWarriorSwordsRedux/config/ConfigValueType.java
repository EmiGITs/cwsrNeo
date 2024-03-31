package com.raptorbk.CyanWarriorSwordsRedux.config;

public enum ConfigValueType {
    BOOLEAN,
    STRING,
    INTEGER,
    LIST,
    FLOAT;

    public static final BooleanConfigValue TRUE = new BooleanConfigValue(true);
    public static final BooleanConfigValue FALSE = new BooleanConfigValue(false);
}
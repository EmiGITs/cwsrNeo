package com.raptorbk.CyanWarriorSwordsRedux.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class SafeConfig {
    private SafeConfig() {}

    public static int getInt(ModConfigSpec.ConfigValue<Integer> value, int defaultValue) {
        try {
            return value.get();
        } catch (IllegalStateException ex) {
            return defaultValue;
        }
    }
}



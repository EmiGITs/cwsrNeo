package com.raptorbk.CyanWarriorSwordsRedux.config;

 
import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
 
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec COMMON;

    private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec CLIENT;

    static
    {
        SwordConfig.init(COMMON_BUILDER, CLIENT_BUILDER);

        COMMON = COMMON_BUILDER.build();
        CLIENT = CLIENT_BUILDER.build();
    }

    public static void loadConfig(ModConfigSpec config, String path)
    {
        // No-op: NeoForge handles loading/saving configs after registration.
    }
}

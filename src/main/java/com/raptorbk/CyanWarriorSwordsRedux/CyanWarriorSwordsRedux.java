package com.raptorbk.CyanWarriorSwordsRedux;


import com.raptorbk.CyanWarriorSwordsRedux.config.Config;
import com.raptorbk.CyanWarriorSwordsRedux.config.ItemConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.*;
import com.raptorbk.CyanWarriorSwordsRedux.data.DataGenerators;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Mod("cwsr")
public class CyanWarriorSwordsRedux {
    public static final String MOD_ID = "cwsr";
    public static Logger logger = LoggerFactory.getLogger(CyanWarriorSwordsRedux.class);
    public CyanWarriorSwordsRedux(IEventBus bus){

        if(!ItemConfig.initialized){
            ItemConfig.load();
        }

        ItemInit.ITEMS.register(bus);

        EnchantmentInit.ENCHANTMENTS.register(bus);

        TriggerInit.TRIGGERS.register(bus);

        LootModifiersInit.LOOT_MODIFIERS.register(bus);

        CreativeModeTabInit.CREATIVE_MODE_TABS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON, "cwsr-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT, "cwsr-client.toml");

        bus.addListener(DataGenerators::gatherData);

        Config.loadConfig(Config.CLIENT, FMLPaths.CONFIGDIR.get().resolve("cwsr-client.toml").toString());
        Config.loadConfig(Config.COMMON, FMLPaths.CONFIGDIR.get().resolve("cwsr-common.toml").toString());

        bus.addListener(FMLClientSetupEvent.class, (fmlClientSetupEvent -> {
            fmlClientSetupEvent.enqueueWork(() -> {
                ModList.get().getModContainerById(MOD_ID).ifPresent(modContainer -> {
                    logger.info("Loaded {}, using version {}", modContainer.getModInfo().getDisplayName(), modContainer.getModInfo().getVersion());
                });
            });
        }));
    }
}

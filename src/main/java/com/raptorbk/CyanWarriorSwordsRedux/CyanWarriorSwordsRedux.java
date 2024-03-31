package com.raptorbk.CyanWarriorSwordsRedux;


import com.raptorbk.CyanWarriorSwordsRedux.core.init.EnchantmentInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.data.DataGenerators;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Mod("cwsr")
public class CyanWarriorSwordsRedux {
    public static final String MOD_ID = "cwsr";
    public static Logger logger = LoggerFactory.getLogger(CyanWarriorSwordsRedux.class);
    public CyanWarriorSwordsRedux(IEventBus bus){

        ItemInit.ITEMS.register(bus);

        EnchantmentInit.ENCHANTMENTS.register(bus);

        bus.addListener(DataGenerators::gatherData);

        bus.addListener(FMLClientSetupEvent.class, (fmlClientSetupEvent -> {
            fmlClientSetupEvent.enqueueWork(() -> {
                ModList.get().getModContainerById(MOD_ID).ifPresent(modContainer -> {
                    logger.info("Loaded {}, using version {}", modContainer.getModInfo().getDisplayName(), modContainer.getModInfo().getVersion());
                });
            });
        }));
    }
}

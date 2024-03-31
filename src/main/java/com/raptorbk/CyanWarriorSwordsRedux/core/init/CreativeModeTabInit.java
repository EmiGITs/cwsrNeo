package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeModeTabInit {
    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CyanWarriorSwordsRedux.MOD_ID);

    // tab title
    public static String CWSR_TAB_TITLE = "CreativeModeTab.cwsrTab";

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CWSR_TAB = CREATIVE_MODE_TABS.register("cwsr_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder();

        builder.displayItems((itemDisplayParameters, output) -> {
            ItemInit.ITEMS.getEntries()
                    .stream()
                    .map(DeferredHolder::get)
                    .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(ItemInit.CYAN_SWORD.get()));
        builder.title(Component.translatable(CWSR_TAB_TITLE));

        return builder.build();
    });
}
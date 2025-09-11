package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.List;

public class CreativeModeTabInit {
    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CyanWarriorSwordsRedux.MOD_ID);

    // tab title
    public static String CWSR_TAB_TITLE = "CreativeModeTab.cwsrTab";

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CWSR_TAB = CREATIVE_MODE_TABS.register("cwsr_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder();

        Collection<DeferredHolder<Item, ? extends Item>> sorted_list= List.of(
                ItemInit.FIRE_SWORD,
                ItemInit.WATER_SWORD,
                ItemInit.EARTH_SWORD,
                ItemInit.WIND_SWORD,
                ItemInit.THUNDER_SWORD,
                ItemInit.DARK_SWORD,
                ItemInit.LIGHT_SWORD,
                ItemInit.ENDER_SWORD,
                ItemInit.BEAST_SWORD,
                ItemInit.COMBUSTION_SWORD,
                ItemInit.ICE_SWORD,
                ItemInit.WILD_NATURE,
                ItemInit.WIND_IMPULSE,
                ItemInit.THUNDER_SHOCK,
                ItemInit.DARK_NETHER,
                ItemInit.LIGHT_NETHER,
                ItemInit.ENDER_PORTAL,
                ItemInit.GOLEM_SWORD,
                ItemInit.ENDER_FIRE,
                ItemInit.ENDER_WIND,
                ItemInit.ENDER_THUNDER,
                ItemInit.PEACEFUL_NATURE,
                ItemInit.TIME_SWORD,
                ItemInit.STEAM_SWORD,
                ItemInit.METEOR_SWORD,
                ItemInit.WIND_BLAST,
                ItemInit.WIND_BOOM,
                ItemInit.THUNDERSTORM_SWORD,
                ItemInit.METEORIC_THUNDERSTORM,
                ItemInit.TRI_ENDER,
                ItemInit.ATLANTIS_SWORD,
                ItemInit.CYAN_SWORD,
                ItemInit.EARTH_ESSENCE,
                ItemInit.BEAST_ESSENCE,
                ItemInit.DARK_ESSENCE,
                ItemInit.ENDER_ESSENCE,
                ItemInit.FIRE_ESSENCE,
                ItemInit.THUNDER_ESSENCE,
                ItemInit.WATER_ESSENCE,
                ItemInit.WIND_ESSENCE,
                ItemInit.LIGHT_ESSENCE,
                ItemInit.MIXED_ESSENCE,
                ItemInit.ABILITY_TOTEM,
                ItemInit.SYNERGY_TOTEM,
                ItemInit.ACTIVE_SYNERGY_TOTEM,
                ItemInit.SWORD_HANDLE,
                ItemInit.TRANSMUTATION_FURNACE
        );

        builder.displayItems((itemDisplayParameters, output) -> {
            sorted_list
                    .stream()
                    .map(DeferredHolder::get)
                    .map(Item::getDefaultInstance)
                    .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(ItemInit.CYAN_SWORD.get()));
        builder.title(Component.translatable(CWSR_TAB_TITLE));

        return builder.build();
    });
}
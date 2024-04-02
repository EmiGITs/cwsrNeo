package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TransmutationFurnace.TransmutationFurnaceBlockEntity;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TransmutationFurnace.TransmutationFurnaceMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuInit {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, CyanWarriorSwordsRedux.MOD_ID);
    public static final DeferredHolder<MenuType<?>, MenuType<TransmutationFurnaceMenu>> TRANSMUTATION_FURNACE_MENU = MENU_TYPES.register("transmutation_furnace", () -> new MenuType<>(TransmutationFurnaceMenu::new, FeatureFlags.DEFAULT_FLAGS));
}


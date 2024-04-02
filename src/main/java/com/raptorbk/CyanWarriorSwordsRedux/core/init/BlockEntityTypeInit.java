package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TransmutationFurnace.TransmutationFurnaceBlockEntity;
import com.raptorbk.CyanWarriorSwordsRedux.recipes.TransmutationRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class BlockEntityTypeInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CyanWarriorSwordsRedux.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TransmutationFurnaceBlockEntity>> TRANSMUTATION_FURNACE = BLOCK_ENTITY_TYPES.register("transmutation_furnace",  () -> BlockEntityType.Builder.of(TransmutationFurnaceBlockEntity::new, BlockInit.TRANSMUTATION_FURNACE_BLOCK.get()).build(null));
}

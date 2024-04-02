package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TransmutationFurnace.TransmutationFurnaceBlock;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.items.SwordHandle;
import com.raptorbk.CyanWarriorSwordsRedux.recipes.TransmutationRecipe;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.ToIntFunction;

public class BlockInit {

    private static ToIntFunction<BlockState> litBlockEmission(int pLightValue) {
        return p_50763_ -> p_50763_.getValue(BlockStateProperties.LIT) ? pLightValue : 0;
    }
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CyanWarriorSwordsRedux.MOD_ID);
    public static final DeferredBlock<TransmutationFurnaceBlock> TRANSMUTATION_FURNACE_BLOCK = BLOCKS.registerBlock("transmutation_furnace",TransmutationFurnaceBlock::new,BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(3.5F)
            .lightLevel(litBlockEmission(13)));


}

package com.raptorbk.CyanWarriorSwordsRedux.core.init.TransmutationFurnace;

import com.raptorbk.CyanWarriorSwordsRedux.core.init.BlockEntityTypeInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.RecipeTypeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TransmutationFurnaceBlockEntity extends AbstractTransmutationFurnaceBlockEntity{
    public TransmutationFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypeInit.TRANSMUTATION_FURNACE, pPos, pBlockState, RecipeTypeInit.TRANSMUTATION.get());
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pId, Inventory pPlayer) {
        return new TransmutationFurnaceMenu(pId, pPlayer, this, this.dataAccess);
    }
}

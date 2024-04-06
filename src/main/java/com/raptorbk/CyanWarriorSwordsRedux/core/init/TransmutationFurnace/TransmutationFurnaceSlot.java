package com.raptorbk.CyanWarriorSwordsRedux.core.init.TransmutationFurnace;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TransmutationFurnaceSlot extends Slot {
    private final AbstractTransmutationFurnaceMenu menu;

    public TransmutationFurnaceSlot(AbstractTransmutationFurnaceMenu pFurnaceMenu, Container pFurnaceContainer, int pSlot, int pXPosition, int pYPosition) {
        super(pFurnaceContainer, pSlot, pXPosition, pYPosition);
        this.menu = pFurnaceMenu;
    }


    @Override
    public boolean mayPlace(ItemStack pStack) {
        return this.menu.isFuel(pStack) || isBucket(pStack);
    }

    @Override
    public int getMaxStackSize(ItemStack pStack) {
        return isBucket(pStack) ? 1 : super.getMaxStackSize(pStack);
    }

    public static boolean isBucket(ItemStack pStack) {
        return pStack.is(Items.BUCKET);
    }
}


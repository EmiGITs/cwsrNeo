package com.raptorbk.CyanWarriorSwordsRedux.core.init.TransmutationFurnace;

import com.raptorbk.CyanWarriorSwordsRedux.core.init.RecipeTypeInit;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.crafting.RecipeType;

public class TransmutationFurnaceMenu extends AbstractTransmutationFurnaceMenu {

    public TransmutationFurnaceMenu(int pContainerId, Inventory pPlayerInventory) {
        super(MenuType.FURNACE, RecipeTypeInit.TRANSMUTATION.get(), RecipeBookType.FURNACE, pContainerId, pPlayerInventory);
    }

    public TransmutationFurnaceMenu(int pContainerId, Inventory pPlayerInventory, Container pFurnaceContainer, ContainerData pFurnaceData) {
        super(MenuType.FURNACE, RecipeTypeInit.TRANSMUTATION.get(), RecipeBookType.FURNACE, pContainerId, pPlayerInventory, pFurnaceContainer, pFurnaceData);
    }
}

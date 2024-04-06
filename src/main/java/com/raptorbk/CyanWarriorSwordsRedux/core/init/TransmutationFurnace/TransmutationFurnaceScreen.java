package com.raptorbk.CyanWarriorSwordsRedux.core.init.TransmutationFurnace;

import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceMenu;

public class TransmutationFurnaceScreen extends AbstractTransmutationFurnaceScreen<TransmutationFurnaceMenu> {
    private static final ResourceLocation LIT_PROGRESS_SPRITE = new ResourceLocation("container/furnace/lit_progress");
    private static final ResourceLocation BURN_PROGRESS_SPRITE = new ResourceLocation("container/furnace/burn_progress");
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");

    public TransmutationFurnaceScreen(TransmutationFurnaceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, new SmeltingRecipeBookComponent(), pPlayerInventory, pTitle, TEXTURE, LIT_PROGRESS_SPRITE, BURN_PROGRESS_SPRITE);
    }
}

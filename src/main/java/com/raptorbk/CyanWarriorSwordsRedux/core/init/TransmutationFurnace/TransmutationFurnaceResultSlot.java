package com.raptorbk.CyanWarriorSwordsRedux.core.init.TransmutationFurnace;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;


public class TransmutationFurnaceResultSlot extends Slot {
    private final Player player;
    private int removeCount;

    public TransmutationFurnaceResultSlot(Player pPlayer, Container pContainer, int pSlot, int pXPosition, int pYPosition) {
        super(pContainer, pSlot, pXPosition, pYPosition);
        this.player = pPlayer;
    }


    @Override
    public boolean mayPlace(ItemStack pStack) {
        return false;
    }


    @Override
    public ItemStack remove(int pAmount) {
        if (this.hasItem()) {
            this.removeCount += Math.min(pAmount, this.getItem().getCount());
        }

        return super.remove(pAmount);
    }

    @Override
    public void onTake(Player pPlayer, ItemStack pStack) {
        this.checkTakeAchievements(pStack);
        super.onTake(pPlayer, pStack);
    }


    @Override
    protected void onQuickCraft(ItemStack pStack, int pAmount) {
        this.removeCount += pAmount;
        this.checkTakeAchievements(pStack);
    }


    @Override
    protected void checkTakeAchievements(ItemStack pStack) {
        pStack.onCraftedBy(this.player.level(), this.player, this.removeCount);
        Player playerx = this.player;
        if (playerx instanceof ServerPlayer serverplayer) {
            Container container = this.container;
            if (container instanceof TransmutationFurnaceBlockEntity abstractfurnaceblockentity) {
                abstractfurnaceblockentity.awardUsedRecipesAndPopExperience(serverplayer);
            }
        }

        this.removeCount = 0;
        net.neoforged.neoforge.event.EventHooks.firePlayerSmeltedEvent(this.player, pStack);
    }
}

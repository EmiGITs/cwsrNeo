package com.raptorbk.CyanWarriorSwordsRedux.core.init.Totems;


import net.minecraft.network.chat.Component;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.*;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


import javax.annotation.Nullable;
import java.util.List;

public class ABILITY_TOTEM extends Item {


    public ABILITY_TOTEM(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        ItemStack x=entity.getItemInHand(handIn);
        Component chatMessage=Component.translatable("chat.cwsr.usage.activation.inh");
        if(!world.isClientSide()) {
            entity.sendSystemMessage(chatMessage);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, x);
    }


}
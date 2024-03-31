package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import net.minecraft.network.chat.Component;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;


import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class SYNERGY_TOTEM extends Item {


    public SYNERGY_TOTEM(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        ItemStack x=entity.getItemInHand(handIn);
        Map<Enchantment, Integer> xEnc= EnchantmentHelper.getEnchantments(x);
        if(entity.isCrouching()){
            if(xEnc.containsKey(EnchantmentInit.INH_ENCHANT.get())){
                xEnc.remove(EnchantmentInit.INH_ENCHANT.get());
                EnchantmentHelper.setEnchantments(xEnc,x);
                Component chatMessage=Component.translatable("chat.cwsr.usage.deactivation.inh");
                if(!world.isClientSide()) {
                    entity.sendSystemMessage(chatMessage);
                }
            }else{
                Component chatMessage=Component.translatable("chat.cwsr.usage.activation.inh");
                if(!world.isClientSide()) {
                    entity.sendSystemMessage(chatMessage);
                }
                x.enchant(EnchantmentInit.INH_ENCHANT.get(),1);
            }

            entity.getCooldowns().addCooldown(x.getItem(), 40);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, x);
        }

        ItemStack active_totemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);
        active_totemStack.enchant(EnchantmentInit.DW_ENCHANT.get(),1);
        if(xEnc.containsKey(EnchantmentInit.INH_ENCHANT.get())){
            active_totemStack.enchant(EnchantmentInit.INH_ENCHANT.get(),1);
        }

        Component chatMessage=Component.translatable("chat.cwsr.usage.activation.dw");
        if(!world.isClientSide()) {
            entity.sendSystemMessage(chatMessage);
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, active_totemStack);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.synergy"));
    }

}

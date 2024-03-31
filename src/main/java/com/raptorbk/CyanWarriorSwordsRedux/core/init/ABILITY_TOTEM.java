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

public class ABILITY_TOTEM extends Item {


    public ABILITY_TOTEM(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        ItemStack x=entity.getItemInHand(handIn);
        Map<Enchantment, Integer> xEnc= EnchantmentHelper.getEnchantments(x);

        if(xEnc.containsKey(EnchantmentInit.INH_ENCHANT.get())) {
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
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, x);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.ability_totem"));
    }


}
package com.raptorbk.CyanWarriorSwordsRedux.core.init.Totems;


import net.minecraft.network.chat.Component;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.*;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

import java.util.List;

import javax.annotation.Nullable;

public class ABILITY_TOTEM extends Item {


    public ABILITY_TOTEM(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        ItemStack x=entity.getItemInHand(handIn);
        HolderLookup.RegistryLookup<Enchantment> enchLookup=world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        ResourceKey<Enchantment> inhKey=ResourceKey.create(Registries.ENCHANTMENT, CyanWarriorSwordsRedux.rl("inh_enchant"));
        Holder<Enchantment> inh=enchLookup.get(inhKey).orElse(null);
        if(inh==null){
            return new InteractionResultHolder<>(InteractionResult.PASS, x);
        }
        ItemEnchantments current=x.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);

        if(current.getLevel(inh)>0) {
            ItemEnchantments.Mutable mut=new ItemEnchantments.Mutable(current);
            mut.set(inh,0);
            x.set(DataComponents.ENCHANTMENTS,mut.toImmutable());
            Component chatMessage=Component.translatable("chat.cwsr.usage.deactivation.inh");
            if(!world.isClientSide()) {
                entity.sendSystemMessage(chatMessage);
            }
        }else{
            Component chatMessage=Component.translatable("chat.cwsr.usage.activation.inh");
            if(!world.isClientSide()) {
                entity.sendSystemMessage(chatMessage);
            }
            ItemEnchantments.Mutable mut=new ItemEnchantments.Mutable(current);
            mut.set(inh,1);
            x.set(DataComponents.ENCHANTMENTS,mut.toImmutable());
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, x);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.ability_totem"));
    }




}
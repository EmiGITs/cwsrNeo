package com.raptorbk.CyanWarriorSwordsRedux.core.init.Totems;

import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

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


import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ACTIVE_SYNERGY_TOTEM extends Item {


    public ACTIVE_SYNERGY_TOTEM(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        ItemStack x=entity.getItemInHand(handIn);

        if(entity.isCrouching()){
            HolderLookup.RegistryLookup<Enchantment> enchLookup=world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            ResourceKey<Enchantment> inhKey=ResourceKey.create(Registries.ENCHANTMENT, CyanWarriorSwordsRedux.rl("inh_enchant"));
            Holder<Enchantment> inh=enchLookup.get(inhKey).orElse(null);
            if(inh!=null){
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
            }
            entity.getCooldowns().addCooldown(x.getItem(), 40);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, x);
        }

        ItemStack totemStack = new ItemStack(ItemInit.SYNERGY_TOTEM.get(),1);
        

        Component chatMessage=Component.translatable("chat.cwsr.usage.deactivation.dw");
        if(!world.isClientSide()) {
            entity.sendSystemMessage(chatMessage);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, totemStack);
    }


    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.synergy"));
    }



}
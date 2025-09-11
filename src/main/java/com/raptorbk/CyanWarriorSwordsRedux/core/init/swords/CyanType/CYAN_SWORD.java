package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.CyanType;


import net.minecraft.core.component.DataComponents;
import javax.annotation.Nonnull;
import com.raptorbk.CyanWarriorSwordsRedux.config.SafeConfig;
import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TriggerInit;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.Entity;

import net.minecraft.world.entity.player.Player;

 
import java.util.List;


public class CYAN_SWORD extends SWORD_CWSR {



    public CYAN_SWORD( float attackSpeedIn, Properties builder) {
            super(Tiers.DIAMOND, SafeConfig.getInt(SwordConfig.CYAN_SWORD_DMG,6), attackSpeedIn, builder);
    }

    public static void callEffect(SurroundEffect seffect, Level world, Player entity, InteractionHand handIn, Block blk){
        seffect.execute(world,entity,handIn,blk);
    }

    public void addEffectsTick(Player playerIn){
        playerIn.addEffect(new MobEffectInstance(MobEffects.REGENERATION,10,4));
    }

    public InteractionResultHolder<ItemStack> use(@Nonnull Level world, @Nonnull Player entity, @Nonnull InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));

        return callerRC(world,entity,handIn, ItemInit.CYAN_SWORD.getId(),0);
    }


    @Override
    public int getMaxDamage(@Nonnull ItemStack stack) {
        return SwordConfig.CYAN_SWORD_DUR.get();
    }


    private static void applyDynamicMods(@Nonnull ItemStack stack) {
        var builder = ItemAttributeModifiers.builder();
        builder.add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(BuiltInRegistries.ITEM.getKey(stack.getItem()), SwordConfig.CYAN_SWORD_DMG.get()+5.0, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND
        );
        builder.add(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(BuiltInRegistries.ITEM.getKey(stack.getItem()), -2.4F, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND
        );
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, builder.build());
    }

    @Override
    public @Nonnull ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        applyDynamicMods(stack);
        return stack;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        applyDynamicMods(stack);
        super.onCraftedBy(stack, worldIn, playerIn);
    }




    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker){
        
        damageAndTriggerIfBreak(stack, attacker, EquipmentSlot.MAINHAND, SwordConfig.ALL_SWORDS_HIT_COST.get());
        return true;
    }

    public void unlockACH(Player entity, Level world){
        if(!(world instanceof ServerLevel)) return;
        ServerPlayer serverPlayer= (ServerPlayer) entity;
        TriggerInit.RR_TRIGGER.get().trigger(serverPlayer);
    }




    @Override
public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level worldIn, @Nonnull Entity entityIn, int itemSlot, boolean isSelected) {

        if(!worldIn.isClientSide){
                applyDynamicMods(stack);
        }

        if(isSelected && !worldIn.isClientSide){
                if(entityIn instanceof Player) {
                        // no-op
                }
        }else{
                if(entityIn instanceof Player) {
                        Player playerIn = (Player) entityIn;

                        ItemStack OffHandItem = playerIn.getOffhandItem();
                        if(OffHandItem.getItem() instanceof  CYAN_SWORD){
                                unlockACH(playerIn,worldIn);
                        }
                }
        }
}
    



    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull Item.TooltipContext context, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.cyan_sword"));
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        ItemStack currentSword = entity.getItemInHand(handIn);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
    }
}

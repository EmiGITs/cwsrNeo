package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.FireType;

import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.*;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.ExecuteSeffect;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;


import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class FIRE_SWORD extends SWORD_CWSR {


    public FIRE_SWORD(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, SwordConfig.FIRE_SWORD_DMG.get(), attackSpeedIn, builder);
    }

    public static void callEffect(SurroundEffect seffect, Level world, Player entity, InteractionHand handIn, Block blk){
        seffect.execute(world,entity,handIn,blk);
    }

    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.FIRE_SWORD_USE_COST.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.fire_sword"));
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        ItemStack currentSword = entity.getItemInHand(handIn);


        Block blk = Blocks.FIRE;
        callEffect(new ExecuteSeffect(), world,entity,handIn,blk);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
    }


    @Override
    public void setCD() {
        this.swordCD=SwordConfig.FIRE_SWORD_COOLDOWN.get();
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        ItemStack currentSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            currentSword.hurtAndBreak(SwordConfig.FIRE_SWORD_USE_COST.get(),entity,Player -> {
                Player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return callerRC(world,entity,handIn,ItemInit.FIRE_SWORD.getId(),SwordConfig.FIRE_SWORD_COOLDOWN.get());

    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.setSecondsOnFire(SwordConfig.FIRE_SWORD_HIT_SEC.get());
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(),attacker,Player -> {
            Player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    public void addEffectsTick(Player playerIn){
        playerIn.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,10,1));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(isSelected && !worldIn.isClientSide){
            if(entityIn instanceof Player) {
                Player playerIn = (Player) entityIn;
                addEffectsTick(playerIn);
            }
        }else{
            if(entityIn instanceof Player) {
                Player playerIn = (Player) entityIn;

                ItemStack OffHandItem = playerIn.getOffhandItem();
                if(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()), ItemInit.FIRE_SWORD.getId())){
                    addEffectsTick(playerIn);
                }
            }
        }
    }
}
package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.EnderType;


import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.ENDER_CLASS_SWORD;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;


import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ENDER_SWORD extends ENDER_CLASS_SWORD {
    public static SimpleTier tierIn = new SimpleTier(3, SwordConfig.ENDER_SWORD_DUR.get(), 0.0f, 4.0f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () ->
            Ingredient.of(Tags.Items.ORES_DIAMOND));


    public ENDER_SWORD( float attackSpeedIn, Properties builder) {
        super(tierIn, SwordConfig.ENDER_SWORD_DMG.get(), attackSpeedIn, builder);
    }

    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.ENDER_SWORD_USE_COST.get();
    }


    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        ItemStack ogSword = entity.getItemInHand(handIn);


        ItemStack itemstack = new ItemStack(Items.ENDER_PEARL);
        world.playSound(null, entity.getX(), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (Mth.nextFloat(world.random,0.0F,1.0F) * 0.4F + 0.8F));
        if (!world.isClientSide) {
            ThrownEnderpearl enderpearlentity = new ThrownEnderpearl(world, entity);
            enderpearlentity.setItem(itemstack);
            enderpearlentity.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.5F, 1.0F);
            ItemStack x = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);
            if(lfActiveSinergyTotem(entity)){
                if(entity.getOffhandItem().getItem() instanceof ENDER_CLASS_SWORD && entity.getMainHandItem().getItem() instanceof ENDER_CLASS_SWORD){
                    if(entity.getOffhandItem().getItem() instanceof ENDER_SWORD){
                        world.addFreshEntity(enderpearlentity);
                    }
                }else{
                    if((Objects.equals(BuiltInRegistries.ITEM.getKey(entity.getOffhandItem().getItem()),ItemInit.METEOR_SWORD.getId()) || Objects.equals(BuiltInRegistries.ITEM.getKey(entity.getOffhandItem().getItem()),ItemInit.METEOR_SWORD.getId())) || (Objects.equals(BuiltInRegistries.ITEM.getKey(entity.getOffhandItem().getItem()),ItemInit.METEORIC_THUNDERSTORM.getId()) || Objects.equals(BuiltInRegistries.ITEM.getKey(entity.getOffhandItem().getItem()),ItemInit.METEORIC_THUNDERSTORM.getId()))  ){
                        this.setDelayThrow(true);
                        this.setThrowEnder(enderpearlentity);
                    }else{
                        world.addFreshEntity(enderpearlentity);
                    }
                }
            }else{
                world.addFreshEntity(enderpearlentity);
            }
        }
        entity.awardStat(Stats.ITEM_USED.get(this));
        if (!entity.getAbilities().instabuild) {
            itemstack.shrink(0);
        }

        itemstack=ogSword;

        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,120,3));

        Random r = new Random();
        int game = r.nextInt(100);

        if(game < 95){
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
        }else{
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,160,1));
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.ender_sword"));
    }

    @Override
    public void setCD() {
        this.swordCD=SwordConfig.ENDER_SWORD_COOLDOWN.get();
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));

        ItemStack ogSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            ogSword.hurtAndBreak(SwordConfig.ENDER_SWORD_USE_COST.get(),entity,Player -> {
                unlockDestroyACH(entity,world);
                Player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return callerRC(world,entity,handIn, ItemInit.ENDER_SWORD.getId(),SwordConfig.ENDER_SWORD_COOLDOWN.get());
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        this.throwEnderPearlEvent(entityIn,worldIn, stack);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(),attacker,Player -> {
            if(attacker instanceof Player){
                unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
            }
            Player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
}
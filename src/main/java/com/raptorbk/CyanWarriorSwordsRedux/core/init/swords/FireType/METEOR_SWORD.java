package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.FireType;

import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.METEOR_CLASS_SWORD;
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

import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraft.util.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;


import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class METEOR_SWORD extends METEOR_CLASS_SWORD {
    public static SimpleTier tierIn = new SimpleTier(BlockTags.NEEDS_DIAMOND_TOOL, SwordConfig.METEOR_SWORD_DUR.get(), 0.0f, 4.0f, 10, () ->
            Ingredient.of(Tags.Items.ORES_DIAMOND));


    public METEOR_SWORD( float attackSpeedIn, Properties builder) {
        super(tierIn, SwordConfig.METEOR_SWORD_DMG.get(), attackSpeedIn, builder);
    }

    public int fireballStrength = 2;


    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.METEOR_SWORD_USE_COST.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.meteor_sword"));
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        ItemStack currentSword = entity.getItemInHand(handIn);

        Vec3 vec3 = entity.getViewVector(1.0F);
        LargeFireball LargeFireball = EntityType.FIREBALL.create((ServerLevel) world);
        LargeFireball.setOwner(entity);
        LargeFireball.setXRot(entity.getXRot());
        LargeFireball.setYRot(entity.getYRot());
        LargeFireball.setPos((int) Math.round(entity.getX()),entity.getY()+2,entity.getZ());
        LargeFireball.shoot(vec3.x, vec3.y, vec3.z, 1.5F, 1.0F);

        ItemStack x = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);
        if(lfActiveSinergyTotem(entity)){
            if(entity.getOffhandItem().getItem() instanceof METEOR_CLASS_SWORD && entity.getMainHandItem().getItem() instanceof METEOR_CLASS_SWORD){
                if(entity.getOffhandItem().getItem() instanceof METEOR_SWORD){
                    this.setDelayMeteor(true);
                    this.setDelayedMeteor(LargeFireball);
                }else{
                    world.addFreshEntity(LargeFireball);
                }

            }else{
                world.addFreshEntity(LargeFireball);
            }
        }else{
            world.addFreshEntity(LargeFireball);
        }


        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
    }

    @Override
    public void setCD() {
        this.swordCD=SwordConfig.METEOR_SWORD_COOLDOWN.get();
    }


    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));

        ItemStack currentSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);




        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            unlockDestroyACH(entity,world);
            currentSword.hurtAndBreak(SwordConfig.METEOR_SWORD_USE_COST.get(), entity, EquipmentSlot.MAINHAND);
        }

        return callerRC(world,entity,handIn,ItemInit.METEOR_SWORD.getId(),SwordConfig.METEOR_SWORD_COOLDOWN.get());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.setRemainingFireTicks(SwordConfig.METEOR_SWORD_HIT_SEC.get() * 20);
        if(attacker instanceof Player){
            unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
        }
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(), attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player entity) {
        world.playSound((Player) null, entity.getX(), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()), SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 0.5F, 0.4F / (Mth.nextFloat(world.random,0.0F,1.0F) * 0.4F + 0.8F));
        //world.explode(entity,entity.getX(),entity.getY(),entity.getZ(),1.0F, Level.ExplosionInteraction.NONE);
    }

    public void addEffectsTick(Player playerIn){
        playerIn.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,10,1));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        if(this.getDelayMeteor() && entityIn instanceof Player && (((Player) entityIn).getMainHandItem()==stack || ((Player) entityIn).getOffhandItem()==stack)){
            if(this.getCount() >= 5){
                this.setCount(0);
                this.setDelayMeteor(false);
                worldIn.addFreshEntity(this.getDelayedMeteor());
                this.setDelayedMeteor(null);
            }else{
                this.setCount(this.getCount()+1);
            }
        }


        if(isSelected && !worldIn.isClientSide){
            if(entityIn instanceof Player) {
                Player playerIn = (Player) entityIn;
                addEffectsTick(playerIn);
            }
        }else{
            if(entityIn instanceof Player) {
                Player playerIn = (Player) entityIn;

                ItemStack OffHandItem = playerIn.getOffhandItem();
                if(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()), ItemInit.METEOR_SWORD.getId())){
                    addEffectsTick(playerIn);
                }
            }
        }
    }
}
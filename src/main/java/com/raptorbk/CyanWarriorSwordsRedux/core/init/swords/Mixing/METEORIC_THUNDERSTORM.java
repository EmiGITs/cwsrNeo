package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.Mixing;


import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.METEOR_CLASS_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TriggerInit;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraft.util.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;


import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class METEORIC_THUNDERSTORM extends METEOR_CLASS_SWORD {
    public static SimpleTier tierIn = new SimpleTier(3, SwordConfig.METEORIC_THUNDERSTORM_DUR.get(), 0.0f, 4.0f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () ->
            Ingredient.of(Tags.Items.ORES_DIAMOND));


    public METEORIC_THUNDERSTORM( float attackSpeedIn, Properties builder) {
        super(tierIn, SwordConfig.METEORIC_THUNDERSTORM_DMG.get(), attackSpeedIn, builder);
    }


    public static void callEffect(SurroundEffect seffect, Level world, Player entity, InteractionHand handIn, Block blk){
        seffect.execute(world,entity,handIn,blk);
    }
    public int fireballStrength = 3;

    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.METEORIC_THUNDERSTORM_USE_COST.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.meteoric_thunderstorm"));
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));



        ItemStack currentSword = entity.getItemInHand(handIn);



        ServerLevel worldSV = (ServerLevel) world;


        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,200,0));
        Vec3 vec3 = entity.getViewVector(1.0F);
        LargeFireball LargeFireball = new LargeFireball(world, entity,vec3.x,vec3.y,vec3.z,fireballStrength);
        LargeFireball.setXRot(entity.getXRot());
        LargeFireball.setYRot(entity.getYRot());
        LargeFireball.setPos((int) Math.round(entity.getX()),entity.getY()+2,entity.getZ());
        LargeFireball.xPower=vec3.x;
        LargeFireball.yPower=vec3.y;
        LargeFireball.zPower=vec3.z;

        ItemStack x = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);
        if(lfActiveSinergyTotem(entity)){
            if(entity.getOffhandItem().getItem() instanceof METEOR_CLASS_SWORD && entity.getMainHandItem().getItem() instanceof METEOR_CLASS_SWORD){
                if(entity.getOffhandItem().getItem() instanceof METEORIC_THUNDERSTORM){
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

        float var4 = 1.0F;
        int j = (int)(entity.yo + (entity.getY() - entity.yo) * (double)var4 + 1.62D - entity.getMyRidingOffset(entity));


        LightningBolt entityBolt = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt.moveTo((int) Math.round(entity.getX()), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()));

        entity.fallDistance=0.0F;
        double motionX = (double)(-Mth.sin(entity.getYRot() / 180.0F * (float)Math.PI) * Mth.cos(entity.getXRot() / 180.0F * (float)Math.PI) * 9F);
        double motionZ = (double)(Mth.cos(entity.getYRot() / 180.0F * (float)Math.PI) * Mth.cos(entity.getXRot() / 180.0F * (float)Math.PI) * 9F);
        double motionY = (double)(-Mth.sin((entity.getXRot() + 0F) / 180.0F * (float)Math.PI) * 9F);
        //entity.push(((double)(-Mth.sin(entity.getYRot() * (float)Math.PI / 180.0F) * (float)j * 0.5F))/8F, 0.1D, ((double)(Mth.cos(entity.getYRot() * (float)Math.PI / 180.0F) * (float)j * 0.5F))/8F);
        entity.push(motionX,motionY,motionZ);
        entity.hurtMarked=true;


        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,10,5));
        //worldSV.addFreshEntity(entityBolt);
        worldSV.addFreshEntity(entityBolt);
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,80,3));
        entity.fallDistance=0.0F;



        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
    }

    @Override
    public void setCD() {
        this.swordCD=SwordConfig.METEORIC_THUNDERSTORM_COOLDOWN.get();
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));
        ItemStack currentSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            currentSword.hurtAndBreak(SwordConfig.METEORIC_THUNDERSTORM_USE_COST.get(),entity,Player -> {
                unlockDestroyACH(entity,world);
                Player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }


        return callerRC(world,entity,handIn,ItemInit.METEORIC_THUNDERSTORM.getId(),SwordConfig.METEORIC_THUNDERSTORM_COOLDOWN.get());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.knockback(2,attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
        target.setSecondsOnFire(SwordConfig.METEORIC_THUNDERSTORM_HIT_SEC.get());
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(),attacker,Player -> {
            if(attacker instanceof Player){
                unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
            }
            Player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public void unlockSEACH(Player entity, Level world) {
        if(!(world instanceof ServerLevel)) return;
        ServerPlayer serverPlayer= (ServerPlayer) entity;
        TriggerInit.SE_TRIGGER.get().trigger(serverPlayer);
        TriggerInit.TM_TRIGGER.get().trigger(serverPlayer);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player entity) {
        unlockSEACH(entity,world);
        world.playSound((Player) null, entity.getX(), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()), SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 0.5F, 0.4F / (Mth.nextFloat(world.random,0.0F,1.0F) * 0.4F + 0.8F));
        //world.explode(entity,entity.getX(),entity.getY(),entity.getZ(),1.0F, Level.ExplosionInteraction.NONE);
    }


    public void addEffectsTick(Player playerIn){
        playerIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,20,4));
        playerIn.addEffect(new MobEffectInstance(MobEffects.JUMP,30,4));
        playerIn.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,40,0));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(this.getDelayMeteor() && entityIn instanceof Player && (((Player) entityIn).getMainHandItem()==stack || ((Player) entityIn).getOffhandItem()==stack)){
            if(this.getCount() >= 10){
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
                if(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()), ItemInit.METEORIC_THUNDERSTORM.getId())){
                    addEffectsTick(playerIn);
                }
            }
        }
    }
}
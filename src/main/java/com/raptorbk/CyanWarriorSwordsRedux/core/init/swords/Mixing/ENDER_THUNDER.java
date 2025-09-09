package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.Mixing;


import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.config.SafeConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.ENDER_CLASS_SWORD;
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
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.crafting.Ingredient;

import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;


import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ENDER_THUNDER extends ENDER_CLASS_SWORD {
    public static SimpleTier tierIn = new SimpleTier(BlockTags.NEEDS_DIAMOND_TOOL, SafeConfig.getInt(SwordConfig.ENDER_THUNDER_DUR, 1000), 0.0f, 4.0f, 10, () ->
            net.minecraft.world.item.crafting.Ingredient.of(Tags.Items.ORES_DIAMOND));


    public ENDER_THUNDER( float attackSpeedIn, Properties builder) {
        super(tierIn, SafeConfig.getInt(SwordConfig.ENDER_THUNDER_DMG, 1), attackSpeedIn, builder);
    }

    public static void callEffect(SurroundEffect seffect, Level world, Player entity, InteractionHand handIn, Block blk){
        seffect.execute(world,entity,handIn,blk);
    }

    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.ENDER_THUNDER_USE_COST.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.ender_thunder"));
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));




        ServerLevel worldSV = (ServerLevel) world;


        LightningBolt entityBolt = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt.moveTo((int) Math.round(entity.getX())+5, (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()-1));



        LightningBolt entityBolt2 = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt2.moveTo((int) Math.round(entity.getX())+5, (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()-1));


        LightningBolt entityBolt3 = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt3.moveTo((int) Math.round(entity.getX())-5, (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()+1));


        LightningBolt entityBolt4 = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt4.moveTo((int) Math.round(entity.getX())-5, (int) Math.round(entity.getY()), (int) Math.round(entity.getZ())-3);

        worldSV.addFreshEntity(entityBolt);
        worldSV.addFreshEntity(entityBolt2);
        worldSV.addFreshEntity(entityBolt3);
        worldSV.addFreshEntity(entityBolt4);


        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,120,3));

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
                    if(entity.getOffhandItem().getItem() instanceof ENDER_THUNDER){
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
    public void setCD() {
        this.swordCD=SwordConfig.ENDER_THUNDER_COOLDOWN.get();
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));

        ItemStack ogSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            unlockDestroyACH(entity,world);
            ogSword.hurtAndBreak(SwordConfig.ENDER_THUNDER_USE_COST.get(), entity, EquipmentSlot.MAINHAND);
        }

        return callerRC(world,entity,handIn,ItemInit.ENDER_THUNDER.getId(), SwordConfig.ENDER_THUNDER_COOLDOWN.get());
    }

    public void addEffectsTick(Player playerIn){
        playerIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,10,2));
    }


    @Override
    public void unlockSEACH(Player entity, Level world) {
        if(!(world instanceof ServerLevel)) return;
        ServerPlayer serverPlayer= (ServerPlayer) entity;
        TriggerInit.SE_TRIGGER.get().trigger(serverPlayer);
        TriggerInit.BB_TRIGGER.get().trigger(serverPlayer);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        this.throwEnderPearlEvent(entityIn,worldIn, stack);
        if(isSelected && !worldIn.isClientSide){
            if(entityIn instanceof Player) {
                Player playerIn = (Player) entityIn;
                addEffectsTick(playerIn);
            }
        }else{
            if(entityIn instanceof Player) {
                Player playerIn = (Player) entityIn;

                ItemStack OffHandItem = playerIn.getOffhandItem();
                if(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()), ItemInit.ENDER_THUNDER.getId())){
                    addEffectsTick(playerIn);
                    this.throwEnderPearlEvent(entityIn,worldIn, stack);
                }
            }
        }
    }
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        if(attacker instanceof Player){
            unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
        }
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(), attacker, EquipmentSlot.MAINHAND);
        return true;
    }
}
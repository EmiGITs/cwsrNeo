package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.ThunderType;


import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.config.SafeConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraft.util.*;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;


import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class THUNDER_SWORD extends SWORD_CWSR {

    public static SimpleTier tierIn = new SimpleTier(
            BlockTags.NEEDS_DIAMOND_TOOL,
            SafeConfig.getInt(SwordConfig.THUNDER_SWORD_DUR, 1000),
            0.0f,
            4.0f,
            10,
            () -> Ingredient.of(Tags.Items.ORES_DIAMOND)
    );


    public THUNDER_SWORD( float attackSpeedIn, Properties builder) {
        super(tierIn, SafeConfig.getInt(SwordConfig.THUNDER_SWORD_DMG, 2), attackSpeedIn, builder);
    }

    public static void callEffect(SurroundEffect seffect, Level world, Player entity, InteractionHand handIn, Block blk){
        seffect.execute(world,entity,handIn,blk);
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.thunder_sword"));
    }

    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.THUNDER_SWORD_USE_COST.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,180,0));

        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));

        ItemStack currentSword = entity.getItemInHand(handIn);



        ServerLevel worldSV = (ServerLevel) world;

        LightningBolt entityBolt = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt.moveTo((int) Math.round(entity.getX()), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ())-2);



        LightningBolt entityBolt2 = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt2.moveTo((int) Math.round(entity.getX()), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ())+2);


        LightningBolt entityBolt3 = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt3.moveTo((int) Math.round(entity.getX())+2, (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()));


        LightningBolt entityBolt4 = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt4.moveTo((int) Math.round(entity.getX())-2, (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()));

        worldSV.addFreshEntity(entityBolt);
        worldSV.addFreshEntity(entityBolt2);
        worldSV.addFreshEntity(entityBolt3);
        worldSV.addFreshEntity(entityBolt4);

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
    }



    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));
        ItemStack currentSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            unlockDestroyACH(entity,world);
            currentSword.hurtAndBreak(SwordConfig.THUNDER_SWORD_USE_COST.get(), entity, EquipmentSlot.MAINHAND);
        }

        return callerRC(world,entity,handIn,ItemInit.THUNDER_SWORD.getId(),SwordConfig.THUNDER_SWORD_COOLDOWN.get());
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player entity) {
        if(!(world instanceof ServerLevel)) return;
        unlockSEACH(entity,world);
        ServerLevel worldSV = (ServerLevel) world;
        LightningBolt entityBolt = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt.moveTo((int) Math.round(entity.getX()), (int) Math.round(entity.getY())+5, (int) Math.round(entity.getZ()));
        worldSV.addFreshEntity(entityBolt);
        world.playSound((Player) null, entity.getX(), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.NEUTRAL, 0.5F, 0.4F / (Mth.nextFloat(world.random,0.0F,1.0F) * 0.4F + 0.8F));
    }

    public void addEffectsTick(Player playerIn){
        playerIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,10,2));
    }

    @Override
    public void setCD() {
        this.swordCD=SwordConfig.THUNDER_SWORD_COOLDOWN.get();
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
                if(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()), ItemInit.THUNDER_SWORD.getId())){
                    addEffectsTick(playerIn);
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

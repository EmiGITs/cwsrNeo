package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.Mixing;


import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.config.SafeConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TriggerInit;
import net.minecraft.network.chat.Component;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.entity.Entity;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.List;


public class WIND_BOOM extends SWORD_CWSR {
    public static SimpleTier tierIn = new SimpleTier(BlockTags.NEEDS_DIAMOND_TOOL, SafeConfig.getInt(SwordConfig.WIND_BOOM_DUR, 1000), 0.0f, 4.0f, 10, () ->
            net.minecraft.world.item.crafting.Ingredient.of(Tags.Items.ORES_DIAMOND));


    public WIND_BOOM( float attackSpeedIn, Properties builder) {
        super(tierIn, SafeConfig.getInt(SwordConfig.WIND_BOOM_DMG, 1), attackSpeedIn, builder);
    }

    public static void callEffect(SurroundEffect seffect, Level world, Player entity, InteractionHand handIn, Block blk){
        seffect.execute(world,entity,handIn,blk);
    }

    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.WIND_BOOM_USE_COST.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.wind_boom"));
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        ItemStack currentSword = entity.getItemInHand(handIn);


        if(!world.isClientSide) {
            if(entity instanceof ServerPlayer){

                if(!world.isClientSide) {
                    if(entity instanceof ServerPlayer){

                        ((ServerPlayer)entity).connection.send(new ClientboundSetEntityMotionPacket(entity));
                        ((ServerPlayer) entity).push(0,1,0);
                        entity.hurtMarked=true;
                        entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,100,1));







                    }
                }




            }
        }

        world.explode(entity,entity.getX(),entity.getY(),entity.getZ(),4.0F, Level.ExplosionInteraction.NONE);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
    }


    @Override
    public void setCD() {
        this.swordCD=SwordConfig.WIND_BOOM_COOLDOWN.get();
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));

        ItemStack currentSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            unlockDestroyACH(entity,world);
            currentSword.hurtAndBreak(SwordConfig.WIND_BOOM_USE_COST.get(), entity, EquipmentSlot.MAINHAND);
        }


        return callerRC(world,entity,handIn,ItemInit.WIND_BOOM.getId(),SwordConfig.WIND_BOOM_COOLDOWN.get());
    }

    @Override
    public void unlockSEACH(Player entity, Level world) {
        if(!(world instanceof ServerLevel)) return;
        ServerPlayer serverPlayer= (ServerPlayer) entity;
        TriggerInit.SE_TRIGGER.get().trigger(serverPlayer);
        TriggerInit.BB_TRIGGER.get().trigger(serverPlayer);
    }


    public void addEffectsTick(Player playerIn){
        playerIn.addEffect(new MobEffectInstance(MobEffects.JUMP,10,2));
    }

    public void unlockACH(Player entity, Level world){
        if(!(world instanceof ServerLevel)) return;
        ServerPlayer serverPlayer= (ServerPlayer) entity;
        TriggerInit.AB_TRIGGER.get().trigger(serverPlayer);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!worldIn.isClientSide){
            if(entityIn instanceof Player playerIn) {

                ItemStack OffHandItem = playerIn.getOffhandItem();
                ItemStack MainHandItem = playerIn.getMainHandItem();
                if((OffHandItem.getItem() instanceof WIND_BLAST && MainHandItem.getItem() instanceof WIND_BOOM) || (MainHandItem.getItem() instanceof WIND_BLAST && OffHandItem.getItem() instanceof WIND_BOOM)){
                    addEffectsTick(playerIn);
                    unlockACH(playerIn,worldIn);
                }
            }

        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.knockback(2,attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
        if(attacker instanceof Player){
            unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
        }
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(), attacker, EquipmentSlot.MAINHAND);
        return true;
    }
}

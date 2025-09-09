package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.WindType;


import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TriggerInit;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.MinecraftServer;
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
import net.minecraft.world.item.enchantment.Enchantments;
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
import java.util.Objects;

public class WIND_IMPULSE extends SWORD_CWSR {
    public static SimpleTier tierIn = new SimpleTier(BlockTags.NEEDS_DIAMOND_TOOL, SwordConfig.WIND_IMPULSE_SWORD_DUR.get(), 0.0f, 4.0f, 10, () ->
            Ingredient.of(Tags.Items.ORES_DIAMOND));


    public WIND_IMPULSE( float attackSpeedIn, Properties builder) {
        super(tierIn, SwordConfig.WIND_IMPULSE_SWORD_DMG.get(), attackSpeedIn, builder);
    }

    public static void callEffect(SurroundEffect seffect, Level world, Player entity, InteractionHand handIn, Block blk){
        seffect.execute(world,entity,handIn,blk);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.wind_impulse"));
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        ItemStack currentSword = entity.getItemInHand(handIn);


        if(!world.isClientSide) {
            if(entity instanceof ServerPlayer){

                ((ServerPlayer)entity).connection.send(new ClientboundSetEntityMotionPacket(entity));
                ((ServerPlayer) entity).push(0,1,0);
                entity.hurtMarked=true;
                entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,100,1));







            }
        }


        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
    }

    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.WIND_IMPULSE_SWORD_USE_COST.get();
    }

    @Override
    public void setCD() {
        this.swordCD=SwordConfig.WIND_IMPULSE_SWORD_COOLDOWN.get();
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));

        ItemStack currentSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            unlockDestroyACH(entity,world);
            currentSword.hurtAndBreak(SwordConfig.WIND_IMPULSE_SWORD_USE_COST.get(), entity, EquipmentSlot.MAINHAND);
        }

        return callerRC(world,entity,handIn,ItemInit.WIND_IMPULSE.getId(),SwordConfig.WIND_IMPULSE_SWORD_COOLDOWN.get());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,900,4));
        target.knockback(2,attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
        if(attacker instanceof Player){
            unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
        }
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(), attacker, EquipmentSlot.MAINHAND);
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
    }


    public void addEffectsTick(Player playerIn){
        playerIn.addEffect(new MobEffectInstance(MobEffects.JUMP,10,4));
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
                if(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()), ItemInit.WIND_IMPULSE.getId())){
                    addEffectsTick(playerIn);
                }
            }
        }
    }
}
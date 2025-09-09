package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.Mixing;


import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.config.SafeConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TriggerInit;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

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

public class THUNDERSTORM_SWORD extends SWORD_CWSR {
    public static SimpleTier tierIn = new SimpleTier(
            BlockTags.NEEDS_DIAMOND_TOOL,
            SafeConfig.getInt(SwordConfig.THUNDERSTORM_SWORD_DUR, 1000),
            0.0f,
            4.0f,
            10,
            () -> Ingredient.of(Tags.Items.ORES_DIAMOND)
    );


    public THUNDERSTORM_SWORD( float attackSpeedIn, Properties builder) {
        super(tierIn, SafeConfig.getInt(SwordConfig.THUNDERSTORM_SWORD_DMG, 1), attackSpeedIn, builder);
    }

    public static void callEffect(SurroundEffect seffect, Level world, Player entity, InteractionHand handIn, Block blk){
        seffect.execute(world,entity,handIn,blk);
    }

    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.THUNDERSTORM_SWORD_USE_COST.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.thunderstorm_sword"));
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));
        ItemStack currentSword = entity.getItemInHand(handIn);



        ServerLevel worldSV = (ServerLevel) world;

        float var4 = 1.0F;

        LightningBolt entityBolt = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt.moveTo((int) Math.round(entity.getX()), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()));
        //Para tocar los valores de impulso, serían los últimos valores de "double motion" (en este caso, 10.5F)
        entity.fallDistance=0.0F;
        double motionX = (double)(-Mth.sin(entity.getYRot() / 180.0F * (float)Math.PI) * Mth.cos(entity.getXRot() / 180.0F * (float)Math.PI) * 7F);
        double motionZ = (double)(Mth.cos(entity.getYRot() / 180.0F * (float)Math.PI) * Mth.cos(entity.getXRot() / 180.0F * (float)Math.PI) * 7F);
        double motionY = (double)(-Mth.sin((entity.getXRot() + 0F) / 180.0F * (float)Math.PI) * 0F);
        //entity.push(((double)(-Mth.sin(entity.getYRot() * (float)Math.PI / 180.0F) * (float)j * 0.5F))/8F, 0.1D, ((double)(Mth.cos(entity.getYRot() * (float)Math.PI / 180.0F) * (float)j * 0.5F))/8F);
        entity.push(motionX,motionY,motionZ);
        entity.hurtMarked=true;


        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,180,0));
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,10,5));
        worldSV.addFreshEntity(entityBolt);

        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,80,3));
        entity.fallDistance=0.0F;


        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
    }

    @Override
    public void setCD() {
        this.swordCD=SwordConfig.THUNDERSTORM_SWORD_COOLDOWN.get();
    }


    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));
        ItemStack currentSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            unlockDestroyACH(entity,world);
            currentSword.hurtAndBreak(SwordConfig.THUNDERSTORM_SWORD_USE_COST.get(), entity, EquipmentSlot.MAINHAND);
        }

        return callerRC(world,entity,handIn,ItemInit.THUNDERSTORM_SWORD.getId(),SwordConfig.THUNDERSTORM_SWORD_COOLDOWN.get());
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

    @Override
    public void unlockSEACH(Player entity, Level world) {
        if(!(world instanceof ServerLevel)) return;
        ServerPlayer serverPlayer= (ServerPlayer) entity;
        TriggerInit.SE_TRIGGER.get().trigger(serverPlayer);
        TriggerInit.BB_TRIGGER.get().trigger(serverPlayer);
    }



    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player entity) {
        if(!world.isClientSide)
        {
            unlockSEACH(entity,world);
            ServerLevel worldSV = (ServerLevel) world;
            LightningBolt entityBolt = EntityType.LIGHTNING_BOLT.create(worldSV);
            entityBolt.moveTo((int) Math.round(entity.getX()), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()));
            worldSV.addFreshEntity(entityBolt);
        }
    }

    public void addEffectsTick(Player playerIn){
        playerIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,10,4));
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
                if(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()), ItemInit.THUNDERSTORM_SWORD.getId())){
                    addEffectsTick(playerIn);
                }
            }
        }
    }
}

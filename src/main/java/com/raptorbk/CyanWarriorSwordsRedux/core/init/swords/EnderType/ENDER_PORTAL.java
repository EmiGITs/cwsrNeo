package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.EnderType;


import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.config.SafeConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TriggerInit;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.StructureTags;
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
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ENDER_PORTAL extends SWORD_CWSR {



    public ENDER_PORTAL( float attackSpeedIn, Properties builder) {
        super(Tiers.DIAMOND, SafeConfig.getInt(SwordConfig.ENDER_PORTAL_SWORD_DMG, 1), attackSpeedIn, builder);
    }



    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.ENDER_PORTAL_SWORD_USE_COST.get();
    }

    @Override
    public int getMaxDamage(@Nonnull ItemStack stack) {
        return SwordConfig.ENDER_PORTAL_SWORD_DUR.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.ender_portal"));
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        applyDynamicMods(stack);
        super.onCraftedBy(stack, worldIn, playerIn);
    }

    @Override
    public @Nonnull ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        applyDynamicMods(stack);
        return stack;
    }

    private static void applyDynamicMods(@Nonnull ItemStack stack) {
        var builder = ItemAttributeModifiers.builder();
        builder.add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(BuiltInRegistries.ITEM.getKey(stack.getItem()), SwordConfig.ENDER_PORTAL_SWORD_DMG.get()+5.0, AttributeModifier.Operation.ADD_VALUE),
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
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        ItemStack ogSword = entity.getItemInHand(handIn);


        ItemStack itemstack = new ItemStack(Items.ENDER_EYE);


        HitResult HitResult = getPlayerPOVHitResult(world, entity, ClipContext.Fluid.NONE);

        entity.startUsingItem(handIn);
        if (world instanceof ServerLevel) {
            BlockPos blockpos = ((ServerLevel) world).findNearestMapStructure(StructureTags.EYE_OF_ENDER_LOCATED, entity.blockPosition(), 100, false);
            if (blockpos != null) {
                EyeOfEnder eyeofenderentity = new EyeOfEnder(world, entity.getX(), entity.getY(0.5D), (int) Math.round(entity.getZ()));
                eyeofenderentity.setItem(itemstack);
                eyeofenderentity.signalTo(blockpos);
                world.addFreshEntity(eyeofenderentity);
                if (entity instanceof ServerPlayer) {
                    CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayer)entity, blockpos);
                }

                world.playSound((Player)null, entity.getX(), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (Mth.nextFloat(world.random,0.0F,1.0F) * 0.4F + 0.8F));
                world.levelEvent((Player)null, 1003, entity.blockPosition(), 0);
                if (!entity.getAbilities().instabuild) {
                    itemstack.shrink(0);
                }

                entity.awardStat(Stats.ITEM_USED.get(this));
                entity.swing(handIn, true);
                itemstack=ogSword;
                return InteractionResultHolder.success(itemstack);
            }
        }
        itemstack=ogSword;


        Random r = new Random();
        int game = r.nextInt(100);

        if(game < 85){
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
        }else{
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,160,1));
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
        }
    }


    @Override
    public void unlockSEACH(Player entity, Level world) {
        if(!(world instanceof ServerLevel)) return;
        ServerPlayer serverPlayer= (ServerPlayer) entity;
        TriggerInit.SE_TRIGGER.get().trigger(serverPlayer);
        TriggerInit.TM_TRIGGER.get().trigger(serverPlayer);
    }


    @Override
    public void setCD() {
        this.swordCD=SwordConfig.ENDER_PORTAL_SWORD_COOLDOWN.get();
    }


    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));

        ItemStack ogSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            ogSword.hurtAndBreak(SwordConfig.ENDER_PORTAL_SWORD_USE_COST.get(), entity, EquipmentSlot.MAINHAND);
        }

        return callerRC(world,entity,handIn, ItemInit.ENDER_PORTAL.getId(),SwordConfig.ENDER_PORTAL_SWORD_COOLDOWN.get());
    }



    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        
        damageAndTriggerIfBreak(stack, attacker, EquipmentSlot.MAINHAND, SwordConfig.ALL_SWORDS_HIT_COST.get());
        Level worldIn = attacker.getCommandSenderWorld();
        Random pushRNG = new Random();
        int gameRNG = pushRNG.nextInt(100);

        if(gameRNG < 25){
            worldIn.playSound((Player) null, target.getX(), target.getY(), target.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.NEUTRAL, 1.0F, 0.4F);
            target.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
            attacker.level().broadcastEntityEvent(target, (byte)46);
            target.setPos(target.getX(),target.getY()+3, target.getZ());
            target.knockback(1,attacker.getX() - target.getX(), attacker.getZ() - target.getZ());

        }else{
            ItemStack itemstack = new ItemStack(Items.CHORUS_FRUIT);
            itemstack.finishUsingItem(worldIn,target);
            itemstack=stack;
        }

        Random r = new Random();
        int game = r.nextInt(100);

        if(game < 85){
            return true;
        }else{
            target.addEffect(new MobEffectInstance(MobEffects.CONFUSION,SwordConfig.ENDER_PORTAL_SWORD_HIT_TK.get(),1));
            return true;
        }

    }


}

package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.DarkType;



import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TriggerInit;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.TooltipFlag;
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
import java.util.Random;

public class DARK_NETHER extends SWORD_CWSR {

    public static SimpleTier tierIn = new SimpleTier(BlockTags.NEEDS_DIAMOND_TOOL, SwordConfig.DARK_NETHER_SWORD_DUR.get(), 0.0f, 4.0f, 10, () ->
            Ingredient.of(Tags.Items.ORES_DIAMOND));


    public DARK_NETHER(float attackSpeedIn, Properties builder) {
        super(tierIn, SwordConfig.DARK_NETHER_SWORD_DMG.get(), attackSpeedIn, new Item.Properties());
    }

    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.DARK_NETHER_SWORD_USE_COST.get();
    }

    @Override
    public void setCD(){
        this.swordCD=SwordConfig.DARK_NETHER_SWORD_COOLDOWN.get();
    }


    @Override
    public void unlockSEACH(Player entity, Level world) {
        if(!(world instanceof ServerLevel)) return;
        ServerPlayer serverPlayer= (ServerPlayer) entity;
        TriggerInit.SE_TRIGGER.get().trigger(serverPlayer);
        TriggerInit.TM_TRIGGER.get().trigger(serverPlayer);

    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.dark_nether"));
    }


    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {

        ItemStack currentSword = entity.getItemInHand(handIn);





        //Creo que esto no genera efecto de wither en el enemigo
        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,20,1));
        Vec3 vec3 = entity.getViewVector(1.0F);
        WitherSkull witherEntity = new WitherSkull(EntityType.WITHER_SKULL, world);
        witherEntity.setXRot(entity.getXRot());
        witherEntity.setYRot(entity.getYRot());
        witherEntity.setPos((int) Math.round(entity.getX()),entity.getY()+2,entity.getZ());
        witherEntity.shoot(vec3.x, vec3.y, vec3.z, 1.5F, 1.0F);
        world.addFreshEntity(witherEntity);
        world.playSound((Player) null, entity.getX(), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()), SoundEvents.WITHER_SHOOT, SoundSource.NEUTRAL, 0.5F, 0.4F / (Mth.nextFloat(world.random,0.0F,1.0F) * 0.4F + 0.8F));

        Random r = new Random();
        int game = r.nextInt(100);

        if(game < 70){
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
        }
        else if (game < 10){
            entity.addEffect(new MobEffectInstance(MobEffects.WITHER,80,1));
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
        }
        else if (game < 10){
            entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,80,1));
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
        }
        else{
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,80,1));
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.addEffect(new MobEffectInstance(MobEffects.WITHER,SwordConfig.DARK_NETHER_SWORD_HIT_TK.get(),0));
        if(attacker instanceof Player){
            unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
        }
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(), attacker, EquipmentSlot.MAINHAND);
        return true;
    }


    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));
        ItemStack currentSword = entity.getItemInHand(handIn);

        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);




        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            unlockDestroyACH(entity,world);
            currentSword.hurtAndBreak(SwordConfig.DARK_NETHER_SWORD_USE_COST.get(), entity, EquipmentSlot.MAINHAND);
        }
        return callerRC(world,entity,handIn, ItemInit.DARK_NETHER.getId(),SwordConfig.DARK_NETHER_SWORD_COOLDOWN.get());
    }
}

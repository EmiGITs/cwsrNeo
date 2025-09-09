package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.ThunderType;


import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TriggerInit;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraft.util.*;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;


import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class THUNDER_SHOCK extends SWORD_CWSR {
    public static SimpleTier tierIn = new SimpleTier(BlockTags.NEEDS_DIAMOND_TOOL, SwordConfig.THUNDER_SHOCK_SWORD_DUR.get(), 0.0f, 4.0f, 10, () ->
            Ingredient.of(Tags.Items.ORES_DIAMOND));


    public THUNDER_SHOCK( float attackSpeedIn, Properties builder) {
        super(tierIn, SwordConfig.THUNDER_SHOCK_SWORD_DMG.get(), attackSpeedIn, builder);
    }

    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.THUNDER_SHOCK_SWORD_USE_COST.get();
    }

    public static void callEffect(SurroundEffect seffect, Level world, Player entity, InteractionHand handIn, Block blk){
        seffect.execute(world,entity,handIn,blk);
    }

    public static BlockHitResult raytraceFromEntity(Entity e, double distance, boolean fluids) {
        Vec3 Vec3 = e.getEyePosition(1);
        Vec3 Vec31 = e.getViewVector(1);
        Vec3 Vec32 = Vec3.add(Vec31.x * distance, Vec31.y * distance, Vec31.z * distance);
        return e.level().clip(new ClipContext(Vec3, Vec32, ClipContext.Block.OUTLINE, fluids ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, e));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.thunder_shock"));
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        int radius=8;
        AABB bb = new AABB((int) Math.round(entity.getX())-radius, (int) Math.round(entity.getY())-radius, (int) Math.round(entity.getZ())-radius, entity.getX()+radius, (int) Math.round(entity.getY())+radius, (int) Math.round(entity.getZ())+radius);
        List<Entity> e = world.getEntities(entity, bb);



        int entCountValid=0;
        for (int i = 0; i <= e.size() - 1; i++) {
            Entity em = e.get(i);
            if (em instanceof LivingEntity && !(em instanceof ArmorStand)){
                entCountValid=entCountValid+1;
            }

        }

        if(!world.isClientSide) {

            if (entCountValid == 0) {
                for (int j = 0; j < 16; ++j) {

                    double d0 = (double) j / 127.0D;
                    double d1 = Mth.lerp(d0, entity.xo, entity.getX()) + (entity.getRandom().nextDouble() - 0.5D) * (double) entity.getBbWidth() * 6.0D;
                    double d2 = Mth.lerp(d0, entity.yo, (int) Math.round(entity.getY())) + entity.getRandom().nextDouble() * (double) entity.getBbHeight();
                    double d3 = Mth.lerp(d0, entity.zo, (int) Math.round(entity.getZ())) + (entity.getRandom().nextDouble() - 0.5D) * (double) entity.getBbWidth() * 6.0D;
                    //world.addParticle(ParticleTypes.ANGRY_VILLAGER, d1, d2, d3, (double) f, (double) f1, (double) f2);
                    if (world instanceof ServerLevel _level)
                    {
                        _level.sendParticles(ParticleTypes.ANGRY_VILLAGER, d1, d2, d3,1,1,0,0,0);

                    }
                }
            }
        }

        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));

        ItemStack currentSword = entity.getItemInHand(handIn);


        ServerLevel worldSV = (ServerLevel) world;


        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,50,4));
        LightningBolt entityBolt = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt.moveTo((int) Math.round(entity.getX()), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()-1));



        LightningBolt entityBolt2 = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt2.moveTo((int) Math.round(entity.getX()), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()+1));


        LightningBolt entityBolt3 = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt3.moveTo((int) Math.round(entity.getX())+1, (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()));


        LightningBolt entityBolt4 = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt4.moveTo((int) Math.round(entity.getX())-1, (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()));

        worldSV.addFreshEntity(entityBolt);
        worldSV.addFreshEntity(entityBolt2);
        worldSV.addFreshEntity(entityBolt3);
        worldSV.addFreshEntity(entityBolt4);
        for (int i = 0; i <= e.size() - 1; i++) {
            Entity em = e.get(i);
            if (em instanceof LivingEntity && !(em instanceof ArmorStand)){
                LightningBolt entityBolt5 = EntityType.LIGHTNING_BOLT.create(worldSV);
                entityBolt5.moveTo((int) Math.round(em.getX()), (int) Math.round(em.getY()),(int) Math.round(em.getZ()));
                worldSV.addFreshEntity(entityBolt5);
            }

        }


        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,150,0));
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
    }

    @Override
    public void setCD() {
        this.swordCD=SwordConfig.THUNDER_SHOCK_SWORD_COOLDOWN.get();
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));
        ItemStack currentSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            unlockDestroyACH(entity,world);
            currentSword.hurtAndBreak(SwordConfig.THUNDER_SHOCK_SWORD_USE_COST.get(), entity, EquipmentSlot.MAINHAND);
        }

        return callerRC(world,entity,handIn, ItemInit.THUNDER_SHOCK.getId(),SwordConfig.THUNDER_SHOCK_SWORD_COOLDOWN.get());
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
        if(!(world instanceof ServerLevel)) return;
        unlockSEACH(entity,world);
        ServerLevel worldSV = (ServerLevel) world;
        LightningBolt entityBolt = EntityType.LIGHTNING_BOLT.create(worldSV);
        entityBolt.moveTo((int) Math.round(entity.getX()), (int) Math.round(entity.getY())+5, (int) Math.round(entity.getZ()));
        worldSV.addFreshEntity(entityBolt);
        world.playSound((Player) null, entity.getX(), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.NEUTRAL, 0.5F, 0.4F / (Mth.nextFloat(world.random,0.0F,1.0F) * 0.4F + 0.8F));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        if(attacker instanceof Player){
            unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
        }
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(), attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    public void addEffectsTick(Player playerIn){
        playerIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,10,4));
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
                if(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()), ItemInit.THUNDER_SHOCK.getId())){
                    addEffectsTick(playerIn);
                }
            }
        }
    }

}

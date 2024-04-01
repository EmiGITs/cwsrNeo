package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.Mixing;


import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.ENDER_CLASS_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.ExecuteSeffect;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
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
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.crafting.Ingredient;

import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;


import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class TRI_ENDER extends ENDER_CLASS_SWORD {
    public static SimpleTier tierIn = new SimpleTier(3, SwordConfig.TRI_ENDER_DUR.get(), 0.0f, 4.0f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () ->
            Ingredient.of(Tags.Items.ORES_DIAMOND));


    public TRI_ENDER( float attackSpeedIn, Properties builder) {
        super(tierIn, SwordConfig.TRI_ENDER_DMG.get(), attackSpeedIn, builder);
    }

    public static void callEffect(SurroundEffect seffect, Level world, Player entity, InteractionHand handIn, Block blk){
        seffect.execute(world,entity,handIn,blk);
    }


    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.TRI_ENDER_USE_COST.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.tri_ender"));
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));

        ServerLevel worldSV = (ServerLevel) world;

        callEffect(new ExecuteSeffect(), world,entity,handIn, Blocks.FIRE);

        ItemStack ogSword = entity.getItemInHand(handIn);



        ItemStack itemstack = new ItemStack(Items.ENDER_PEARL);
        world.playSound(null, entity.getX(), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (Mth.nextFloat(world.random,0.0F,1.0F) * 0.4F + 0.8F));

        ItemStack currentSword = entity.getItemInHand(handIn);
        int radius=8;
        AABB bb = new AABB((int) Math.round(entity.getX())-radius, (int) Math.round(entity.getY())-radius, (int) Math.round(entity.getZ())-radius, entity.getX()+radius, (int) Math.round(entity.getY())+radius, (int) Math.round(entity.getZ())+radius);
        List<Entity> e = world.getEntities(entity, bb);


        if(!world.isClientSide) {
            int entCountValid = 0;
            for (int i = 0; i <= e.size() - 1; i++) {
                Entity em = e.get(i);
                if (em instanceof LivingEntity && !(em instanceof ArmorStand)) {
                    entCountValid = entCountValid + 1;
                }

            }

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

        if (!world.isClientSide) {
            for (int i = 0; i <= e.size() - 1; i++) {
                Entity em = e.get(i);
                if (em instanceof ServerPlayer && !(em instanceof ArmorStand) && !em.isSpectator()){
                    if(!(((ServerPlayer) em).isCreative()) && !(((ServerPlayer) em).getAbilities().flying)){
                        ((ServerPlayer)em).connection.send(new ClientboundSetEntityMotionPacket(em));
                        ((ServerPlayer) em).knockback(2,entity.getX() - (int) Math.round(em.getX()), (int) Math.round(entity.getZ()) -(int) Math.round(em.getZ()));
                        em.hurtMarked=true;
                    }
                }else{
                    if (em instanceof LivingEntity && !(em instanceof ArmorStand)){
                        ((LivingEntity) em).knockback(2,entity.getX() - (int) Math.round(em.getX()), (int) Math.round(entity.getZ()) -(int) Math.round(em.getZ()));
                    }
                }
            }


            ThrownEnderpearl enderpearlentity = new ThrownEnderpearl(world, entity);
            enderpearlentity.setItem(itemstack);
            enderpearlentity.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.5F, 1.0F);
            ItemStack x = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);
            if(entity.getInventory().contains(x)){
                if(entity.getOffhandItem().getItem() instanceof ENDER_CLASS_SWORD && entity.getMainHandItem().getItem() instanceof ENDER_CLASS_SWORD){
                    if(entity.getOffhandItem().getItem() instanceof TRI_ENDER){
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
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,80,3));



        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public void setCD() {
        this.swordCD=SwordConfig.TRI_ENDER_COOLDOWN.get();
    }


    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        ItemStack ogSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && entity.getInventory().contains(ActiveSynergyTotemStack)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            ogSword.hurtAndBreak(SwordConfig.TRI_ENDER_USE_COST.get(),entity,Player -> {
                unlockDestroyACH(entity,world);
                Player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return callerRC(world,entity,handIn,ItemInit.TRI_ENDER.getId(),SwordConfig.TRI_ENDER_COOLDOWN.get());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.setSecondsOnFire(SwordConfig.TRI_ENDER_HIT_SEC.get());
        target.knockback(2,attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(),attacker,Player -> {
            if(attacker instanceof Player){
                unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
            }
            Player.broadcastBreakEvent(EquipmentSlot.MAINHAND);        });
        return true;
    }

    public void addEffectsTick(Player playerIn){
        playerIn.addEffect(new MobEffectInstance(MobEffects.JUMP,10,3));
        playerIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,10,3));
        playerIn.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,10,1));
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
                if(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()), ItemInit.TRI_ENDER.getId())){
                    addEffectsTick(playerIn);
                }
            }
        }
    }
}

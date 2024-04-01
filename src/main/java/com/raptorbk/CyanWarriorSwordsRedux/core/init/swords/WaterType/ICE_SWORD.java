package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.WaterType;

import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TriggerInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.minecraft.world.entity.Entity;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.levelgen.feature.SnowAndFreezeFeature;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class ICE_SWORD extends SWORD_CWSR {
    public static SimpleTier tierIn = new SimpleTier(3, SwordConfig.ICE_SWORD_DUR.get(), 0.0f, 4.0f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () ->
            Ingredient.of(Tags.Items.ORES_DIAMOND));


    public ICE_SWORD( float attackSpeedIn, Properties builder) {
        super(tierIn, SwordConfig.ICE_SWORD_DMG.get(), attackSpeedIn, builder);
    }

    public static void callEffect(SurroundEffect seffect, Level world, Player entity, InteractionHand handIn, Block blk){
        seffect.execute(world,entity,handIn,blk);
    }



    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.ICE_SWORD_USE_COST.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.ice_sword"));
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        ItemStack currentSword = entity.getItemInHand(handIn);

        int radius=6;
        int entCountValid=0;
        AABB bb = new AABB((int) Math.round(entity.getX())-radius, (int) Math.round(entity.getY())-radius, (int) Math.round(entity.getZ())-radius, entity.getX()+radius, (int) Math.round(entity.getY())+radius, (int) Math.round(entity.getZ())+radius);
        List<Entity> e = world.getEntities(entity, bb);
        for (int i = 0; i <= e.size() - 1; i++) {
            Entity em = e.get(i);
            if (em instanceof LivingEntity && !(em instanceof ArmorStand)){
                entCountValid=entCountValid+1;
                em.setTicksFrozen(500);
                ((LivingEntity) em).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,200,4));

                ((LivingEntity) em).getRandom();
                for(int j = 0; j < 64; ++j) {

                    double d0 = (double)j / 127.0D;
                    float f = (((LivingEntity) em).getRandom().nextFloat() - 0.5F) * 0.2F;
                    float f1 = (((LivingEntity) em).getRandom().nextFloat() - 0.5F) * 0.2F;
                    float f2 = (((LivingEntity) em).getRandom().nextFloat() - 0.5F) * 0.2F;
                    double d1 = Mth.lerp(d0, em.xo, (int) Math.round(em.getX())) + (((LivingEntity) em).getRandom().nextDouble() - 0.5D) * (double)em.getBbWidth() * 2.0D;
                    double d2 = Mth.lerp(d0, em.yo, (int) Math.round(em.getY())) + ((LivingEntity) em).getRandom().nextDouble() * (double)em.getBbHeight();
                    double d3 = Mth.lerp(d0, em.zo,(int) Math.round(em.getZ())) + (((LivingEntity) em).getRandom().nextDouble() - 0.5D) * (double)em.getBbWidth() * 2.0D;
                    //world.addParticle(ParticleTypes.ITEM_SNOWBALL, d1, d2, d3, (double)f, (double)f1, (double)f2);
                    if (world instanceof ServerLevel _level)
                    {
                        _level.sendParticles(ParticleTypes.ITEM_SNOWBALL, d1, d2, d3,1,1,0,0,0);

                    }
                }
                //
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
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
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
        this.swordCD=SwordConfig.ICE_SWORD_COOLDOWN.get();
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));
        ItemStack currentSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && entity.getInventory().contains(ActiveSynergyTotemStack)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            currentSword.hurtAndBreak(SwordConfig.ICE_SWORD_USE_COST.get(), entity,Player -> {
                unlockDestroyACH(entity,world);
                Player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return callerRC(world,entity,handIn,ItemInit.ICE_SWORD.getId(),SwordConfig.ICE_SWORD_COOLDOWN.get());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,SwordConfig.ICE_SWORD_HIT_TK.get(),3));
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(),attacker,Player -> {
            if(attacker instanceof Player){
                unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
            }
            Player.broadcastBreakEvent(EquipmentSlot.MAINHAND);        });
        return true;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player entity) {

        //Ojo con esto, esquiva cualquier tipo de bloqueo
        unlockSEACH(entity,world);
        BlockPos pos = new BlockPos((int) Math.round(entity.getX()), (int) Math.round(entity.getY())+3, (int) Math.round(entity.getZ()));
        world.setBlock(pos, Blocks.ICE.defaultBlockState(),1);
    }

    public void addEffectsTick(Player playerIn){
        playerIn.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING,10,4));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(isSelected && !worldIn.isClientSide){
            if(entityIn instanceof Player) {
                LivingEntity tempEntity=(LivingEntity) entityIn;
                BlockPos playerBlockStandingPos=entityIn.blockPosition();
                FrostWalkerEnchantment.onEntityMoved(tempEntity,worldIn,playerBlockStandingPos,2);
            }
        }else{
            if(entityIn instanceof Player) {
                Player playerIn = (Player) entityIn;

                ItemStack OffHandItem = playerIn.getOffhandItem();
                if(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()), ItemInit.ICE_SWORD.getId())){
                    LivingEntity tempEntity=(LivingEntity) entityIn;
                    BlockPos playerBlockStandingPos=entityIn.blockPosition();
                    FrostWalkerEnchantment.onEntityMoved(tempEntity,worldIn,playerBlockStandingPos,2);
                }
            }
        }
    }


}

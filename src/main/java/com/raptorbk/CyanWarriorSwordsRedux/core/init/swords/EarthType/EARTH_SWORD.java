package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.EarthType;


import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.ExecuteSeffect;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.block.Blocks;
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

public class EARTH_SWORD extends SWORD_CWSR {

    public static SimpleTier tierIn = new SimpleTier(3, SwordConfig.EARTH_SWORD_DUR.get(), 0.0f, 4.0f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () ->
            Ingredient.of(Tags.Items.ORES_DIAMOND));


    public EARTH_SWORD( float attackSpeedIn, Properties builder) {
        super(tierIn, SwordConfig.EARTH_SWORD_DMG.get(), attackSpeedIn, builder);
    }


    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        ItemStack currentSword = entity.getItemInHand(handIn);


        Block blk = Blocks.DIRT;
        callEffect(new ExecuteSeffect(), world,entity,handIn,blk);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
    }

    public static void callEffect(SurroundEffect seffect, Level world, Player entity, InteractionHand handIn, Block blk){
        seffect.execute(world,entity,handIn,blk);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.earth_sword"));
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));
        ItemStack currentSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            currentSword.hurtAndBreak(SwordConfig.EARTH_SWORD_USE_COST.get(),entity,Player -> {
                unlockDestroyACH(entity,world);
                Player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        if(Objects.equals(BuiltInRegistries.ITEM.getKey(entity.getOffhandItem().getItem()),ItemInit.WILD_NATURE.getId())){
            SWORD_CWSR x = (SWORD_CWSR) entity.getOffhandItem().getItem();
            x.setDamageBool(true);
            x.setBlocker(true);
            InteractionHand f = InteractionHand.OFF_HAND;
            if(!(entity.getCooldowns().isOnCooldown(x))){
                ((SWORD_CWSR) x.asItem()).eventRC(world,entity,f,entity.getOffhandItem());
                entity.getCooldowns().addCooldown(x.asItem(), SwordConfig.WILD_NATURE_SWORD_COOLDOWN.get());
            }
            return super.use(world,entity,handIn);
        }else{
            if(Objects.equals(BuiltInRegistries.ITEM.getKey(entity.getOffhandItem().getItem()),ItemInit.EARTH_SWORD)){
                return callerRC(world,entity,handIn,ItemInit.EARTH_SWORD.getId(),SwordConfig.EARTH_SWORD_COOLDOWN.get());
            }else{
                if(Objects.equals(BuiltInRegistries.ITEM.getKey(entity.getOffhandItem().getItem()),ItemInit.WATER_SWORD)){
                    return super.use(world,entity,handIn);
                }else{
                    return callerRC(world,entity,handIn,ItemInit.EARTH_SWORD.getId(),SwordConfig.EARTH_SWORD_COOLDOWN.get());
                }
            }
        }
    }


    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.EARTH_SWORD_USE_COST.get();
    }

    public void addEffectsTick(Player playerIn){
        playerIn.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,10,1));
    }


    @Override
    public void setCD() {
        this.swordCD=SwordConfig.EARTH_SWORD_COOLDOWN.get();
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
                if(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()), ItemInit.EARTH_SWORD.getId())){
                    addEffectsTick(playerIn);
                }
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(),attacker,Player -> {
            if(attacker instanceof Player){
                unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
            }
            Player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
}

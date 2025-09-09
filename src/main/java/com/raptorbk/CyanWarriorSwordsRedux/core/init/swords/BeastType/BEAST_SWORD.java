package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.BeastType;

import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.config.SafeConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.List;

public class BEAST_SWORD extends SWORD_CWSR {

    public static SimpleTier tierIn = new SimpleTier(BlockTags.NEEDS_DIAMOND_TOOL, SafeConfig.getInt(SwordConfig.BEAST_SWORD_DUR, 1000), 0.0f, 4.0f, 10, () ->
            net.minecraft.world.item.crafting.Ingredient.of(Tags.Items.ORES_DIAMOND));

    public BEAST_SWORD(float attackSpeedIn, Properties builder) {
        super(tierIn, SafeConfig.getInt(SwordConfig.BEAST_SWORD_DMG, 2), attackSpeedIn, builder);
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {

        ItemStack currentSword = entity.getItemInHand(handIn);

        Vec3 look = entity.getLookAngle();
        Wolf wolfProjectile = new Wolf(EntityType.WOLF,world);
        wolfProjectile.setPos((int) Math.round(entity.getX()),entity.getY()+1,entity.getZ());
        wolfProjectile.setDeltaMovement(look.x,look.y,look.z);
        wolfProjectile.tame(entity);
        world.addFreshEntity(wolfProjectile);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
    }


    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.BEAST_SWORD_USE_COST.get();
    }

    @Override
    public void setCD() {
        this.swordCD=SwordConfig.BEAST_SWORD_COOLDOWN.get();
    }



    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));

        ItemStack currentSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);


        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            unlockDestroyACH(entity,world);
            currentSword.hurtAndBreak(SwordConfig.BEAST_SWORD_USE_COST.get(), entity, EquipmentSlot.MAINHAND);
        }

        return callerRC(world,entity,handIn,ItemInit.BEAST_SWORD.getId(),SwordConfig.BEAST_SWORD_COOLDOWN.get());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        if(attacker instanceof Player){
            unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
        }
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(), attacker, EquipmentSlot.MAINHAND);
        return true;
    }


    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.beast_sword"));
    }




    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player entity) {
        unlockSEACH(entity,world);
        world.playSound((Player) null, entity.getX(), (int) Math.round(entity.getY()), (int) Math.round(entity.getZ()), SoundEvents.WOLF_GROWL, SoundSource.NEUTRAL, 0.5F, 1.0f);
    }

}
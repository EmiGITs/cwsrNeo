package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.DarkType;

import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;


import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class DARK_SWORD extends SWORD_CWSR {


    public static SimpleTier tierIn = new SimpleTier(BlockTags.NEEDS_DIAMOND_TOOL, SwordConfig.DARK_SWORD_DUR.get(), 0.0f, 4.0f, 10, () ->
            Ingredient.of(Tags.Items.ORES_DIAMOND));


    public DARK_SWORD( float attackSpeedIn, Properties builder) {
        super(tierIn, SwordConfig.DARK_SWORD_DMG.get(), attackSpeedIn, builder);
    }

    @Override
    public void setCD() {
        this.swordCD=SwordConfig.DARK_SWORD_COOLDOWN.get();
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));
        ItemStack currentSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            unlockDestroyACH(entity,world);
            currentSword.hurtAndBreak(SwordConfig.DARK_SWORD_USE_COST.get(), entity, EquipmentSlot.MAINHAND);
        }

        return callerRC(world,entity,handIn, ItemInit.DARK_NETHER.getId(),SwordConfig.DARK_SWORD_COOLDOWN.get());
    }

    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.DARK_SWORD_USE_COST.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.dark_sword"));
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        ItemStack currentSword = entity.getItemInHand(handIn);


        entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,200,4));

        Random r = new Random();
        int game = r.nextInt(100);

        if(game < 94){
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
        }
        else if (game < 96){
            entity.addEffect(new MobEffectInstance(MobEffects.WITHER,80,1));
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
        }
        else if (game < 98){
            entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,200,1));
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
        }
        else{
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,80,1));
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
        }
    }
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.addEffect(new MobEffectInstance(MobEffects.WITHER, SwordConfig.DARK_SWORD_WITHER_HIT_TK.get(),4));
        target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,SwordConfig.DARK_SWORD_BLIND_HIT_TK.get(),4));
        if(attacker instanceof Player){
            unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
        }
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(), attacker, EquipmentSlot.MAINHAND);
        return true;
    }
}

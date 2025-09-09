package com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.Mixing;


import com.raptorbk.CyanWarriorSwordsRedux.config.SwordConfig.SwordConfig;
import com.raptorbk.CyanWarriorSwordsRedux.config.SafeConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SWORD_CWSR;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SwordHabilities.SurroundEffect;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TriggerInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.Entity;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;


import javax.annotation.Nullable;
import java.util.List;

public class STEAM_SWORD extends SWORD_CWSR {
    public static SimpleTier tierIn = new SimpleTier(
            BlockTags.NEEDS_DIAMOND_TOOL,
            SafeConfig.getInt(SwordConfig.STEAM_SWORD_DUR, 1000),
            0.0f,
            4.0f,
            10,
            () -> Ingredient.of(Tags.Items.ORES_DIAMOND)
    );


    public STEAM_SWORD( float attackSpeedIn, Properties builder) {
        super(tierIn, SafeConfig.getInt(SwordConfig.STEAM_SWORD_DMG, 1), attackSpeedIn, builder);
    }
    public static void callEffect(SurroundEffect seffect, Level world, Player entity, InteractionHand handIn, Block blk){
        seffect.execute(world,entity,handIn,blk);
    }


    @Override
    public void setDamagePU() {
        this.damagePU=SwordConfig.STEAM_SWORD_USE_COST.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.cwsr.steam_sword"));
    }

    @Override
    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {
        ItemStack currentSword = entity.getItemInHand(handIn);

        int radius = 8;
        AABB bb = new AABB((int) Math.round(entity.getX()) - radius, (int) Math.round(entity.getY()) - radius, (int) Math.round(entity.getZ()) - radius, entity.getX() + radius, (int) Math.round(entity.getY()) + radius, (int) Math.round(entity.getZ()) + radius);
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


        if(!world.isClientSide) {
            for (int i = 0; i <= e.size() - 1; i++) {
                Entity em = e.get(i);
                if (em instanceof ServerPlayer && !(em instanceof ArmorStand) && !em.isSpectator()){
                    if(!(((ServerPlayer) em).isCreative()) && !(((ServerPlayer) em).getAbilities().flying)){
                        ((ServerPlayer)em).connection.send(new ClientboundSetEntityMotionPacket(em));
                        ((ServerPlayer) em).push(0,1,0);
                        em.hurtMarked=true;
                    }
                }else{
                    if (em instanceof LivingEntity && !(em instanceof ArmorStand)){
                        em.push(0, 1, 0);
                    }
                }
            }
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
    }

    @Override
    public void setCD() {
        this.swordCD=SwordConfig.STEAM_SWORD_COOLDOWN.get();
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand handIn) {
        if(!(world instanceof ServerLevel)) return new InteractionResultHolder<>(InteractionResult.PASS, entity.getItemInHand(handIn));
        ItemStack currentSword = entity.getItemInHand(handIn);
        ItemStack ActiveSynergyTotemStack = new ItemStack(ItemInit.ACTIVE_SYNERGY_TOTEM.get(),1);

        if(!lfAbilityTotem(entity) && ((entity.getMainHandItem() != entity.getItemInHand(handIn) && entity.getMainHandItem().getItem() instanceof SWORD_CWSR && lfActiveSinergyTotem(entity)) || entity.getMainHandItem() == entity.getItemInHand(handIn) || (entity.getOffhandItem()==entity.getItemInHand(handIn) && !(entity.getMainHandItem().getItem() instanceof SWORD_CWSR)))){
            unlockDestroyACH(entity,world);
            currentSword.hurtAndBreak(SwordConfig.STEAM_SWORD_USE_COST.get(), entity, EquipmentSlot.MAINHAND);
        }

        return callerRC(world,entity,handIn, ItemInit.STEAM_SWORD.getId(),SwordConfig.STEAM_SWORD_COOLDOWN.get());
    }

    @Override
    public void unlockSEACH(Player entity, Level world) {
        if(!(world instanceof ServerLevel)) return;
        ServerPlayer serverPlayer= (ServerPlayer) entity;
        TriggerInit.SE_TRIGGER.get().trigger(serverPlayer);
        TriggerInit.BB_TRIGGER.get().trigger(serverPlayer);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        target.setRemainingFireTicks(SwordConfig.STEAM_SWORD_HIT_SEC.get() * 20);
        if(attacker instanceof Player){
            unlockDestroyACH((Player) attacker,attacker.getCommandSenderWorld());
        }
        stack.hurtAndBreak(SwordConfig.ALL_SWORDS_HIT_COST.get(), attacker, EquipmentSlot.MAINHAND);
        return true;
    }
}

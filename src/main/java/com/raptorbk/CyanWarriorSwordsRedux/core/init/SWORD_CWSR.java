package com.raptorbk.CyanWarriorSwordsRedux.core.init;


import com.raptorbk.CyanWarriorSwordsRedux.core.init.Totems.ABILITY_TOTEM;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.Totems.ACTIVE_SYNERGY_TOTEM;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.Totems.SYNERGY_TOTEM;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;

import net.minecraft.world.entity.Entity;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.SwordItem;

import net.minecraft.world.level.Level;


import java.util.List;
import javax.annotation.Nonnull;
import java.util.Objects;

public class SWORD_CWSR extends SwordItem {
    public boolean firstExec=true;
    public boolean blocker=false;
    public int damagePU=0;
    public boolean damageBool=false;
    public int webPos;
    public int count=0;
    public boolean delayThrow=false;
    public int swordCD=0;
    public ThrownEnderpearl throwEnder=null;
    public SWORD_CWSR(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, builder.attributes(SwordItem.createAttributes(tier, attackDamageIn, attackSpeedIn)));
    }


    public int getSwordCD() {return this.swordCD;}

    public void setThrowEnder(ThrownEnderpearl throwEnder) {
        this.throwEnder = throwEnder;
    }

    public void setDelayThrow(boolean x){
        this.delayThrow=x;
    }

    public boolean getDelayThrow(){
        return this.delayThrow;
    }

    public int getCount(){
        return this.count;
    }

    public void setCount(int x){
        this.count=x;
    }


    public void throwEnderPearlEvent(Entity entityIn,Level worldIn, ItemStack stack){
        if(this.getDelayThrow() && entityIn instanceof Player && (((Player) entityIn).getMainHandItem()==stack || ((Player) entityIn).getOffhandItem()==stack)){
            if(this.getCount() >= 5){
                this.setCount(0);
                this.setDelayThrow(false);
                worldIn.addFreshEntity(this.getThrowEnder());
                this.setThrowEnder(null);
            }else{
                this.setCount(this.getCount()+1);
            }
        }
    }








    public ThrownEnderpearl getThrowEnder() {
        return this.throwEnder;
    }

    public int getWebPos(){
        return this.webPos;
    }

    public void setWebPos(int x){
        this.webPos=x;
    }

    public void setDamageBool(boolean x){
        this.damageBool=x;
    }

    public void setDamagePU(){
        this.damagePU=0;
    }

    public void setEnderPearlPos(int x){
        this.damagePU=30;
    }


    public int getDamagePU(){
        return this.damagePU;
    }

    public void setBlocker(boolean x){
        this.blocker=x;
    }

    public boolean getDamageBool(){
        return this.damageBool;
    }

    public void useRightClick(Level worldIn, Player playerIn, InteractionHand hand) {
        this.firstExec=false;
        this.asItem().use(worldIn, playerIn, hand);
    }

    public InteractionResultHolder<ItemStack> eventRC(Level world, Player entity, InteractionHand handIn, ItemStack OffHandItem) {

        ItemStack currentSword = entity.getItemInHand(handIn);

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, currentSword);
    }

    public void unlockSEACH(Player entity, Level world){
        if(!(world instanceof ServerLevel)) return;

    }



    @Override
    public void onCraftedBy(@Nonnull ItemStack stack, @Nonnull Level worldIn, @Nonnull Player playerIn) {
        unlockSEACH(playerIn,worldIn);
        super.onCraftedBy(stack, worldIn, playerIn);
    }

    public boolean lfAbilityTotem(Player entity){
        List<ItemStack> listItems = entity.getInventory().items;
        boolean isINH=false;
        for (ItemStack temp : listItems) {
            if(temp.getItem() instanceof ABILITY_TOTEM){
                if(checkINH(temp)){
                    isINH=true;
                }
                break;
            }
        }
        return isINH;
    }


    public boolean lfActiveSinergyTotem(Player entity){
        List<ItemStack> listItems = entity.getInventory().items;
        boolean isACTIVE=false;
        for (ItemStack temp : listItems) {
            if(temp.getItem() instanceof ACTIVE_SYNERGY_TOTEM){
                    isACTIVE=true;
                break;
            }
        }
        return isACTIVE;
    }




    public boolean checkINH(ItemStack totemItem){
        CustomData data = totemItem.get(DataComponents.CUSTOM_DATA);
        if (data == null) return false;
        CompoundTag tag = data.copyTag();
        return tag.getBoolean("cwsr_inh");
    }

    public void setCD(){ }

    public void unlockDWACH(Player entity, Level world){
        if(!(world instanceof ServerLevel)) return;
        ServerPlayer serverPlayer= (ServerPlayer) entity;
        TriggerInit.DWE_TRIGGER.get().trigger(serverPlayer);
    }

    public void unlockDestroyACH(Player entity, Level world){
        if(!(world instanceof ServerLevel)) return;
        ServerPlayer serverPlayer= (ServerPlayer) entity;
        TriggerInit.SD_TRIGGER.get().trigger(serverPlayer);
    }

    protected static void damageAndTriggerIfBreak(ItemStack stack, net.minecraft.world.entity.LivingEntity user, EquipmentSlot slot, int cost){
        int currentDamage = stack.getDamageValue();
        int maxDamage = stack.getMaxDamage();
        boolean willBreak = currentDamage + cost >= maxDamage;
        stack.hurtAndBreak(cost, user, slot);
        if (willBreak && user instanceof Player) {
            Player player = (Player) user;
            Level world = player.getCommandSenderWorld();
            if (world instanceof ServerLevel) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                TriggerInit.SD_TRIGGER.get().trigger(serverPlayer);
            }
        }
    }



    public InteractionResultHolder<ItemStack> callerRC(Level world, Player entity, InteractionHand handIn, ResourceLocation swordCH, int CooldownRC) {


        ItemStack OffHandItem = entity.getOffhandItem();
        ItemStack MainHandItem= entity.getMainHandItem();
        ItemStack ReturnableItem=null;





        if(Objects.equals(BuiltInRegistries.ITEM.getKey(MainHandItem.getItem()), swordCH)){
            ReturnableItem=MainHandItem;
        }else{
            ReturnableItem=OffHandItem;
        }

        

        List<ItemStack> listItems = entity.getInventory().items;
        boolean isINH=false;
        int totemHits=0;
        for (ItemStack temp : listItems) {
            if(totemHits==2){
                break;
            }
            if(temp.getItem() instanceof ABILITY_TOTEM || temp.getItem() instanceof SYNERGY_TOTEM || temp.getItem() instanceof ACTIVE_SYNERGY_TOTEM){
                totemHits=totemHits+1;
                if(checkINH(temp)){
                    if(isINH==false){
                        isINH=true;
                    }
                }

            }
        }

        if(isINH){
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, ReturnableItem);
        }




        //Si la espada se encuentra en la mano izquierda pero hay otra en la derecha, la derecha se encarga de todo

        if(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()), swordCH)   && MainHandItem.getItem() instanceof SWORD_CWSR & !(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()),BuiltInRegistries.ITEM.getKey(MainHandItem.getItem())))){
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, ReturnableItem);
        }

        if(firstExec){
            if(!(MainHandItem.getItem() instanceof SWORD_CWSR)){  //Mano izquierda y en la derecha nada u otra cosa

                entity.getCooldowns().addCooldown(ReturnableItem.getItem(), CooldownRC);

                return eventRC(world,entity,handIn,ReturnableItem);
            }else if (MainHandItem.getItem() instanceof SWORD_CWSR && OffHandItem.getItem() instanceof SWORD_CWSR){{  //Las dos son espadas cwsr

                if(!(entity.getCooldowns().isOnCooldown(OffHandItem.getItem()))){


                    if(lfActiveSinergyTotem(entity)){


                        if(!(Objects.equals(BuiltInRegistries.ITEM.getKey(OffHandItem.getItem()),BuiltInRegistries.ITEM.getKey(MainHandItem.getItem())))){
                            if(!blocker){
                                ((SWORD_CWSR) OffHandItem.getItem()).setDamagePU();
                                OffHandItem.hurtAndBreak(((SWORD_CWSR) OffHandItem.getItem()).getDamagePU(), entity, EquipmentSlot.OFFHAND);
                                ((SWORD_CWSR) OffHandItem.getItem()).setCD();
                                entity.getCooldowns().addCooldown(OffHandItem.getItem(), ((SWORD_CWSR) OffHandItem.getItem()).getSwordCD());
                                ((SWORD_CWSR) OffHandItem.getItem()).eventRC(world, entity, handIn,OffHandItem);
                                unlockDWACH(entity,world);
                            }else{
                                this.setBlocker(false);
                                this.setDamageBool(false);
                            }
                        }
                        this.firstExec=true;
                    }
                }
                if(MainHandItem != OffHandItem){
                    entity.getCooldowns().addCooldown(ReturnableItem.getItem(), CooldownRC);
                }

                return eventRC(world,entity,handIn,ReturnableItem);
            }}else{
                entity.getCooldowns().addCooldown(ReturnableItem.getItem(), CooldownRC);
                return eventRC(world,entity,handIn,ReturnableItem);  //Mano derecha
            }
        }else{
            if(MainHandItem != OffHandItem){
                entity.getCooldowns().addCooldown(ReturnableItem.getItem(), CooldownRC);
            }
            this.firstExec=true;
            return eventRC(world,entity,handIn,ReturnableItem);
        }
    }
}
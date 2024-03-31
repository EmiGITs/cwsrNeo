package com.raptorbk.CyanWarriorSwordsRedux.core.init.Enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class INH_ENCHANTMENT extends Enchantment {
    public INH_ENCHANTMENT(){
        super(Rarity.RARE, EnchantmentCategory.WEAPON,new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 40;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 41;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }
}
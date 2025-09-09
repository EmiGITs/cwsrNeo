package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EnchantmentInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, CyanWarriorSwordsRedux.MOD_ID);
}

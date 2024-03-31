package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class EnchantmentInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, CyanWarriorSwordsRedux.MOD_ID);


    /* create enchantment */
    public static final Supplier<Enchantment> INH_ENCHANT= ENCHANTMENTS.register("inh_enchant", INH_ENCHANTMENT::new);

    public static final Supplier<Enchantment> DW_ENCHANT= ENCHANTMENTS.register("dw_enchant", DW_ENCHANTMENT::new);

}

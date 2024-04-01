package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import com.mojang.serialization.Codec;
import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.LootModifiers.RollExtraTablesLootModifier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class LootModifiersInit {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, CyanWarriorSwordsRedux.MOD_ID);
    public static void init() {}

    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<RollExtraTablesLootModifier>> ROLL_EXTRA_TABLES = registerSerializer("roll_extra_tables", RollExtraTablesLootModifier.CODEC);

    private static <T extends LootModifier> DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<T>> registerSerializer(String id, Codec<T> codec) {
        return LOOT_MODIFIERS.register(id, () -> codec);
    }
}
package com.raptorbk.CyanWarriorSwordsRedux;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class Conditions
{
    public static class EnableNormalCrafting implements ICondition
    {
        public static final Codec<EnableNormalCrafting> CODEC = Codec.unit(EnableNormalCrafting::new);

        @Override
        public boolean test(IContext context)
        {
            return true;
        }

        @Override
        public Codec<? extends ICondition> codec()
        {
            return CODEC;
        }
    }

    public static class EnableTransmutationCrafting implements ICondition
    {
        public static final Codec<EnableTransmutationCrafting> CODEC = Codec.unit(EnableTransmutationCrafting::new);

        @Override
        public boolean test(IContext context)
        {
            return true;
        }

        @Override
        public Codec<? extends ICondition> codec()
        {
            return CODEC;
        }
    }
}
package com.raptorbk.CyanWarriorSwordsRedux;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
public class Conditions
{
    public static class EnableNormalCrafting implements ICondition
    {
        public static final MapCodec<EnableNormalCrafting> CODEC = MapCodec.unit(EnableNormalCrafting::new);

        @Override
        public boolean test(IContext context)
        {
            return true;
        }

        @Override
        public MapCodec<? extends ICondition> codec()
        {
            return CODEC;
        }
    }

    public static class EnableTransmutationCrafting implements ICondition
    {
        public static final MapCodec<EnableTransmutationCrafting> CODEC = MapCodec.unit(EnableTransmutationCrafting::new);

        @Override
        public boolean test(IContext context)
        {
            return true;
        }

        @Override
        public MapCodec<? extends ICondition> codec()
        {
            return CODEC;
        }
    }
}
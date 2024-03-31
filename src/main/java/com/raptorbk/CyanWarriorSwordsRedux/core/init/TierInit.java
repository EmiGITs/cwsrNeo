package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class TierInit {
    public static SimpleTier SWORD = new SimpleTier(3, 2000, 0f, 0f, 10, BlockTags.NEEDS_DIAMOND_TOOL, () ->
            Ingredient.of(Tags.Items.ORES_DIAMOND));
}
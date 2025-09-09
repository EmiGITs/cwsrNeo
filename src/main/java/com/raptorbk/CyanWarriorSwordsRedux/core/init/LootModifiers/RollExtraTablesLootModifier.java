package com.raptorbk.CyanWarriorSwordsRedux.core.init.LootModifiers;


import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RollExtraTablesLootModifier extends LootModifier {
    public static final MapCodec<RollExtraTablesLootModifier> CODEC = RecordCodecBuilder.mapCodec(builder -> codecStart(builder).and(
            ResourceLocation.CODEC.listOf().fieldOf("tables").forGetter(instance -> instance.additionalTables)
    ).apply(builder, RollExtraTablesLootModifier::new));

    private final List<ResourceLocation> additionalTables;

    public RollExtraTablesLootModifier(LootItemCondition[] conditions, List<ResourceLocation> additionalTables) {
        super(conditions);

        this.additionalTables = additionalTables;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @NotNull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (ResourceLocation tableLocation : additionalTables) {
            LootTable table = context.getLevel().getServer().reloadableRegistries().getLootTable(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.LOOT_TABLE, tableLocation));
            boolean compatible = true;

            for (LootContextParam<?> param : table.getParamSet().getRequired()) {
                if (!context.hasParam(param)) {
                    compatible = false;
                    break;
                }
            }

            if (compatible && table != LootTable.EMPTY)
                table.getRandomItems(context, generatedLoot::add);
        }

        return generatedLoot;
    }
}
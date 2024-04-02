package com.raptorbk.CyanWarriorSwordsRedux;


import com.mojang.datafixers.util.Pair;
import com.raptorbk.CyanWarriorSwordsRedux.config.Config;
import com.raptorbk.CyanWarriorSwordsRedux.config.ItemConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.*;
import com.raptorbk.CyanWarriorSwordsRedux.data.DataGenerators;
import com.raptorbk.CyanWarriorSwordsRedux.data.recipe.TransmutationRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;


@Mod("cwsr")
public class CyanWarriorSwordsRedux {
    public static final String MOD_ID = "cwsr";
    public static Logger logger = LoggerFactory.getLogger(CyanWarriorSwordsRedux.class);
    public CyanWarriorSwordsRedux(IEventBus bus){

        if(!ItemConfig.initialized){
            ItemConfig.load();
        }

        BlockEntityTypeInit.BLOCK_ENTITY_TYPES.register(bus);

        BlockInit.BLOCKS.register(bus);

        RecipeTypeInit.RECIPE_TYPES.register(bus);

        SerializerInit.RECIPE_SERIALIZERS.register(bus);

        ItemInit.ITEMS.register(bus);

        EnchantmentInit.ENCHANTMENTS.register(bus);

        TriggerInit.TRIGGERS.register(bus);

        LootModifiersInit.LOOT_MODIFIERS.register(bus);

        CreativeModeTabInit.CREATIVE_MODE_TABS.register(bus);

        bus.addListener(this::gatherData);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON, "cwsr-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT, "cwsr-client.toml");

        bus.addListener(DataGenerators::gatherData);

        Config.loadConfig(Config.CLIENT, FMLPaths.CONFIGDIR.get().resolve("cwsr-client.toml").toString());
        Config.loadConfig(Config.COMMON, FMLPaths.CONFIGDIR.get().resolve("cwsr-common.toml").toString());

        bus.addListener(FMLClientSetupEvent.class, (fmlClientSetupEvent -> {
            fmlClientSetupEvent.enqueueWork(() -> {
                ModList.get().getModContainerById(MOD_ID).ifPresent(modContainer -> {
                    logger.info("Loaded {}, using version {}", modContainer.getModInfo().getDisplayName(), modContainer.getModInfo().getVersion());
                });
            });
        }));
    }

    public void gatherData(GatherDataEvent event)
    {
        DataGen.gatherData(event);
    }

    public static class DataGen {
        public static void gatherData(GatherDataEvent event) {
            DataGenerator gen = event.getGenerator();

            gen.addProvider(event.includeServer(), new Recipes(gen.getPackOutput(), event.getLookupProvider()));
        }

        private static class Recipes extends RecipeProvider implements IConditionBuilder {
            public Recipes(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
                super(output, lookup);
            }

            @Override
            protected void buildRecipes(RecipeOutput consumer) {
                CompoundTag compoundtag = new CompoundTag();

                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.BEAST_ESSENCE.get(),compoundtag,5,5)
                        .addMaterial(Ingredient.of(ItemInit.BEAST_SWORD.get()), 1)
                        .addCriterion("has_leather", has(itemTag("forge:leather")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.BEAST_ESSENCE));
            }
            }

            private static ResourceLocation transmutationRecipeId(DeferredHolder<?, ?> item)
            {
                return transmutationRecipeId(item.getId());
            }

            private static ResourceLocation transmutationRecipeId(ResourceLocation item)
            {
                return  new ResourceLocation(item.getNamespace(), item.getPath() + "_via_transmutation");
            }

            public final ItemStack stack(ItemLike item, CompoundTag tag)
            {
                ItemStack stack = new ItemStack(item);
                stack.setTag(tag);
                return stack;
            }

            @SafeVarargs
            public final CompoundTag compound(Pair<String, Tag>... entries)
            {
                CompoundTag tag = new CompoundTag();
                for (Pair<String, Tag> entry : entries)
                {
                    tag.put(entry.getFirst(), entry.getSecond());
                }
                return tag;
            }
    }

    private static TagKey<Item> itemTag(String name)
    {
        return TagKey.create(Registries.ITEM, new ResourceLocation(name));
    }

}

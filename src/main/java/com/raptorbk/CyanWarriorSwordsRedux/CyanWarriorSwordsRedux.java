package com.raptorbk.CyanWarriorSwordsRedux;


import com.mojang.datafixers.util.Pair;
import com.raptorbk.CyanWarriorSwordsRedux.config.Config;
import com.raptorbk.CyanWarriorSwordsRedux.config.ItemConfig;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.*;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TransmutationFurnace.TransmutationFurnaceMenu;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.TransmutationFurnace.TransmutationFurnaceScreen;
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
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
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

       bus.addListener(EventPriority.NORMAL, false, RegisterMenuScreensEvent.class, CyanWarriorSwordsRedux::registerMenuScreens);

        MenuInit.MENU_TYPES.register(bus);

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

    private static void registerMenuScreens(RegisterMenuScreensEvent ev) {
        ev.register(MenuInit.TRANSMUTATION_FURNACE_MENU.get(), TransmutationFurnaceScreen::new);
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

                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.BEAST_ESSENCE.get(),1,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.BEAST_SWORD.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.BEAST_ESSENCE, ItemInit.BEAST_SWORD));

                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.BEAST_ESSENCE.get(),2,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.GOLEM_SWORD.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.BEAST_ESSENCE, ItemInit.GOLEM_SWORD));


                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.DARK_ESSENCE.get(),1,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.DARK_SWORD.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.DARK_ESSENCE, ItemInit.DARK_SWORD));

                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.DARK_ESSENCE.get(),2,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.DARK_NETHER.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.DARK_ESSENCE, ItemInit.DARK_NETHER));


                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.EARTH_ESSENCE.get(),1,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.EARTH_SWORD.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.EARTH_ESSENCE, ItemInit.EARTH_SWORD));

                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.EARTH_ESSENCE.get(),2,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.WILD_NATURE.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.EARTH_ESSENCE, ItemInit.WILD_NATURE));



                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.ENDER_ESSENCE.get(),1,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.ENDER_SWORD.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.ENDER_ESSENCE, ItemInit.ENDER_SWORD));

                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.ENDER_ESSENCE.get(),2,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.ENDER_PORTAL.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.ENDER_ESSENCE, ItemInit.ENDER_PORTAL));


                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.FIRE_ESSENCE.get(),1,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.FIRE_SWORD.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.FIRE_ESSENCE, ItemInit.FIRE_SWORD));

                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.FIRE_ESSENCE.get(),2,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.COMBUSTION_SWORD.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.FIRE_ESSENCE, ItemInit.COMBUSTION_SWORD));



                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.LIGHT_ESSENCE.get(),1,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.LIGHT_SWORD.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.LIGHT_ESSENCE, ItemInit.LIGHT_SWORD));

                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.LIGHT_ESSENCE.get(),2,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.LIGHT_NETHER.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.LIGHT_ESSENCE, ItemInit.LIGHT_NETHER));


                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.THUNDER_ESSENCE.get(),1,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.THUNDER_SWORD.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.THUNDER_ESSENCE, ItemInit.THUNDER_SWORD));

                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.THUNDER_ESSENCE.get(),2,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.THUNDER_SHOCK.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.THUNDER_ESSENCE, ItemInit.THUNDER_SHOCK));


                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.WATER_ESSENCE.get(),1,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.WATER_SWORD.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.WATER_ESSENCE, ItemInit.WATER_SWORD));

                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.WATER_ESSENCE.get(),2,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.ICE_SWORD.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.WATER_ESSENCE, ItemInit.ICE_SWORD));


                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.WIND_ESSENCE.get(),1,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.WIND_SWORD.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.WIND_ESSENCE, ItemInit.WIND_SWORD));

                TransmutationRecipeBuilder.begin(RecipeCategory.MISC, ItemInit.WIND_ESSENCE.get(),2,compoundtag,5,1000)
                        .addMaterial(Ingredient.of(ItemInit.WIND_IMPULSE.get()), 1)
                        .addCriterion("has_item", has(itemTag("cwsr:sword_handle")))
                        .save(consumer.withConditions(
                                modLoaded("cwsr")
                        ), transmutationRecipeId(ItemInit.WIND_ESSENCE, ItemInit.WIND_IMPULSE));




            }
            }

            private static ResourceLocation transmutationRecipeId(DeferredHolder<?, ?> item, DeferredHolder<?, ?> item2)
            {
                return transmutationRecipeId(item.getId(), item2.getId());
            }

            private static ResourceLocation transmutationRecipeId(ResourceLocation item, ResourceLocation item2)
            {
                return  new ResourceLocation(item.getNamespace(), item.getPath() + "_via_transmutation"+"_from_"+item2.getPath());
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

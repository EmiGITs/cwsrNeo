package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.recipes.TransmutationRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SerializerInit {

    //public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.RECIPE_SERIALIZERS, CyanWarriorSwordsReduxMod.MOD_ID);
    //public static final RegistryObject<RecipeSerializer<?>> TRANSMUTATION_RECIPE = RECIPE_SERIALIZERS.register("transmutation", () -> new TransmutationRecipeSerializer<>(TransmutationRecipe::new,200));
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CyanWarriorSwordsRedux.MOD_ID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TransmutationRecipe>> TRANSMUTATION = RECIPE_SERIALIZERS.register("transmutation", TransmutationRecipe.Serializer::new); {
    };
}

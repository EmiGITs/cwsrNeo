package com.raptorbk.CyanWarriorSwordsRedux.core.init;
import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.recipes.TransmutationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class RecipeTypeInit {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, CyanWarriorSwordsRedux.MOD_ID);
    public static final DeferredHolder<RecipeType<?>, RecipeType<TransmutationRecipe>> TRANSMUTATION = RECIPE_TYPES.register("transmutation", () -> new RecipeType<>() {
    });
}
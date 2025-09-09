package com.raptorbk.CyanWarriorSwordsRedux.compat;


import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.compat.transmutation.TransmutationRecipeCategory;

import com.raptorbk.CyanWarriorSwordsRedux.core.init.BlockInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.RecipeTypeInit;
import com.raptorbk.CyanWarriorSwordsRedux.recipes.TransmutationRecipe;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class CyanWarriorSwordsJEIPlugin implements IModPlugin {
    public static IJeiHelpers jeiHelpers;

    private static final ResourceLocation ID = CyanWarriorSwordsRedux.rl("jei_plugin");

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(BlockInit.TRANSMUTATION_FURNACE_BLOCK.asItem()), TransmutationRecipeCategory.RECIPE_TYPE);
    }
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration){
        jeiHelpers = registration.getJeiHelpers();

        registration.addRecipeCategories(new TransmutationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

    }


    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration){
        var level = Minecraft.getInstance().level;
        if (level == null) return; // client world not ready
        var recipeManager = level.getRecipeManager();
        List<RecipeHolder<TransmutationRecipe>> transmutation_Recipes = recipeManager.getAllRecipesFor(RecipeTypeInit.TRANSMUTATION.get());

        List<TransmutationRecipe> transmutation_converted=new ArrayList<>();

        int i;
        for (i = 0; i < transmutation_Recipes.size(); i++)
        {
            transmutation_converted.add(transmutation_Recipes.get(i).value());
        }

        registration.addRecipes(TransmutationRecipeCategory.RECIPE_TYPE, transmutation_converted);
    }

    @Override
    public ResourceLocation getPluginUid()
    {
        return ID;
    }
}
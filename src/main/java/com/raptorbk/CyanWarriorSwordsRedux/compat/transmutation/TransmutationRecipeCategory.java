package com.raptorbk.CyanWarriorSwordsRedux.compat.transmutation;



import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.BlockInit;
import com.raptorbk.CyanWarriorSwordsRedux.recipes.TransmutationRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;

public class TransmutationRecipeCategory implements IRecipeCategory<TransmutationRecipe> {
    public static final RecipeType<TransmutationRecipe> RECIPE_TYPE = RecipeType.create(CyanWarriorSwordsRedux.MOD_ID, "transmutation", TransmutationRecipe.class);


    public static final ResourceLocation UID = CyanWarriorSwordsRedux.rl("transmutation");
    public static final ResourceLocation BACKGROUNDRL = CyanWarriorSwordsRedux.rl("gui/jei/transmutation_furnace.png");

    @Nonnull
    private final IDrawable background;
    private final IDrawable icon;

    public TransmutationRecipeCategory(IGuiHelper guiHelper)
    {
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(BlockInit.TRANSMUTATION_FURNACE_BLOCK.asItem()));
        background = guiHelper.createDrawable(BACKGROUNDRL, 0, 0, 170, 167);
    }




    @Nonnull
    @Override
    public Component getTitle()
    {
        Component string_title_jei_transmutation_category=Component.translatable("jei.cwsr.titles.transmutation");
        return string_title_jei_transmutation_category;
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon()
    {
        return icon;
    }


    @Override
    public RecipeType<TransmutationRecipe> getRecipeType()
    {
        return RECIPE_TYPE;
    }




    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder recipeLayout, @Nonnull TransmutationRecipe recipe, @Nonnull IFocusGroup ingredients)
    {

        recipeLayout.addSlot(RecipeIngredientRole.OUTPUT, 115, 34).addItemStack(recipe.getResultItem());
        recipeLayout.addSlot(RecipeIngredientRole.INPUT, 55, 16).addIngredients(recipe.getIngredients().get(0));

    }







}

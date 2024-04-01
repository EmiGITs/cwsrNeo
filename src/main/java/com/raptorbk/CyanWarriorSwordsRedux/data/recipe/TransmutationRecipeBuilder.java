package com.raptorbk.CyanWarriorSwordsRedux.data.recipe;

import com.raptorbk.CyanWarriorSwordsRedux.recipes.TransmutationRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.ToolAction;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class TransmutationRecipeBuilder
{
    private final RecipeCategory category;
    private String group;

    private final NonNullList<TransmutationRecipe.Material> materials = NonNullList.create();
    private final Item result;
    private final int count;
    @Nullable
    private final CompoundTag tag;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private final float experience;

    private final int cookingtime;

    public static TransmutationRecipeBuilder begin(RecipeCategory cat, Item result, float experience, int cookingtime)
    {

        return begin(cat, result, experience, cookingtime);
    }

    public static TransmutationRecipeBuilder begin(RecipeCategory cat, Item result, int count, float experience, int cookingtime)
    {
        return begin(cat, result, count, cookingtime);
    }

    public static TransmutationRecipeBuilder begin(RecipeCategory cat, Item result, CompoundTag tag, float experience, int cookingtime)
    {
        return begin(cat, result, 1, tag,experience,cookingtime);
    }

    public static TransmutationRecipeBuilder begin(RecipeCategory cat, Item result, int count, @Nullable CompoundTag tag, float experience, int cookingtime)
    {
        return new TransmutationRecipeBuilder(cat, result, count, tag, experience, cookingtime);
    }

    protected TransmutationRecipeBuilder(RecipeCategory cat, Item result, int count, @Nullable CompoundTag tag, float experience, int cookingtime)
    {
        this.category = cat;
        this.result = result;
        this.count = count;
        this.experience = experience;
        this.cookingtime = cookingtime;
        this.tag = tag;
    }









    public TransmutationRecipeBuilder addMaterial(int count, ItemLike... x)
    {
        return addMaterial(Ingredient.of(x), count);
    }

    public TransmutationRecipeBuilder addMaterial(ItemLike x, int count)
    {
        return addMaterial(Ingredient.of(x), count);
    }

    public TransmutationRecipeBuilder addMaterial(TagKey<Item> x, int count)
    {
        return addMaterial(Ingredient.of(x), 1);
    }

    public TransmutationRecipeBuilder addMaterial(ItemLike... x)
    {
        return addMaterial(Ingredient.of(x), 1);
    }

    public TransmutationRecipeBuilder addMaterial(TagKey<Item> x)
    {
        return addMaterial(Ingredient.of(x), 1);
    }

    public TransmutationRecipeBuilder addMaterial(Ingredient x)
    {
        return addMaterial(x, 1);
    }

    public TransmutationRecipeBuilder addMaterial(Ingredient x, int count)
    {
        if (materials.size() >= 4)
        {
            throw new IllegalArgumentException("There can only be up to 4 materials!");
        }
        if (count <= 0)
        {
            throw new IllegalArgumentException("Count must be a positive integer!");
        }
        materials.add(TransmutationRecipe.Material.of(x, count));
        return this;
    }

    public TransmutationRecipeBuilder addCriterion(String name, Criterion<?> criterionIn)
    {
        this.criteria.put(name, criterionIn);
        return this;
    }

    public TransmutationRecipeBuilder setGroup(String groupIn)
    {
        this.group = groupIn;
        return this;
    }



    public void save(RecipeOutput consumerIn, ResourceLocation id)
    {
        this.validate(id);

        var advancementBuilder = Advancement.Builder.advancement();
        advancementBuilder
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(advancementBuilder::addCriterion);
        ResourceLocation advancementId = id.withPrefix("recipes/" + category.getFolderName() + "/" );

        var resultStack = new ItemStack(this.result, this.count);
        resultStack.setTag(this.tag);

        var recipe = new TransmutationRecipe(
                Objects.requireNonNullElse(this.group, ""),
                /*RecipeBuilder.determineBookCategory(this.category),*/
                this.materials,
                resultStack,
                this.experience,
                this.cookingtime);

        consumerIn.accept(
                id,
                recipe,
                advancementBuilder.build(advancementId));
    }

    private void validate(ResourceLocation id)
    {
        if (this.criteria.isEmpty())
        {
            throw new IllegalStateException("No way of obtaining transmutation recipe " + id);
        }
        if (this.materials.isEmpty())
        {
            throw new IllegalStateException("No ingredients for transmutation recipe " + id);
        }
    }
}
package com.raptorbk.CyanWarriorSwordsRedux.recipes;


import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import com.raptorbk.CyanWarriorSwordsRedux.core.init.RecipeTypeInit;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.SerializerInit;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransmutationRecipe implements Recipe<Container>
{
    public static <T extends TransmutationRecipe> Products.P5<RecordCodecBuilder.Mu<T>, String, NonNullList<Material>, ItemStack, Float, Integer>
    defaultTransmutationFields(RecordCodecBuilder.Instance<T> instance)
    {
        return instance.group(
                ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(TransmutationRecipe::getGroup),
                NonNullList.codecOf(Material.CODEC).fieldOf("materials").forGetter(TransmutationRecipe::getMaterials),
                ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(TransmutationRecipe::getOutput),
                Codec.FLOAT.fieldOf("experience").forGetter(TransmutationRecipe::getExperience),
                Codec.INT.fieldOf("cookingtime").forGetter(TransmutationRecipe::getCookingTime));
    }

    public static final Codec<TransmutationRecipe> CODEC = RecordCodecBuilder.create(instance -> defaultTransmutationFields(instance).apply(instance, TransmutationRecipe::new));

    private final String group;

    private final NonNullList<Material> materials;


    private final ItemStack output;

    public final float experience;
    public final int cookTime;

    public TransmutationRecipe(String group, NonNullList<Material> materials, ItemStack output, float experienceIn, int cookTimeIn)
    {
        this.group = group;
        this.materials = materials;
        this.output = output;
        this.experience = experienceIn;
        this.cookTime = cookTimeIn;
    }

    public static Collection<RecipeHolder<TransmutationRecipe>> getAllRecipes(Level world)
    {
        return world.getRecipeManager().getAllRecipesFor(RecipeTypeInit.TRANSMUTATION.get());
    }

    @Override
    public String getGroup()
    {
        return group;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return width * height >= 4;
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        NonNullList<Ingredient> allIngredients = NonNullList.create();
        materials.stream().map(m -> m.ingredient).forEach(allIngredients::add);
        return allIngredients;
    }

    @Override
    public boolean matches(Container inv, Level worldIn)
    {

        Map<Ingredient, Integer> missing = materials.stream().collect(Collectors.toMap(i -> i.ingredient, i -> i.count));
        for (int i = 0; i < 3; i++)
        {
            for (Map.Entry<Ingredient, Integer> mat : missing.entrySet())
            {
                Ingredient ing = mat.getKey();
                int value = mat.getValue();
                ItemStack stack = inv.getItem(i);
                if (ing.test(stack))
                {
                    int remaining = Math.max(0, value - stack.getCount());
                    mat.setValue(remaining);
                }
            }
        }

        return missing.values().stream().noneMatch(v -> v > 0);

    }

    @Override
    public ItemStack assemble(Container p_44001_, RegistryAccess registryAccess)
    {
        return getResultItem(registryAccess).copy();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess)
    {
        return output;
    }

    public ItemStack getResultItem()
    {
        return output;
    }

    public NonNullList<Material> getMaterials()
    {
        return materials;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return SerializerInit.TRANSMUTATION.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return RecipeTypeInit.TRANSMUTATION.get();
    }






    public ItemStack getOutput()
    {
        return output;
    }

    public Float getExperience(){
        return experience;
    }

    public Integer getCookingTime(){
        return cookTime;
    }



    public static class Serializer extends SerializerBase<TransmutationRecipe>
    {
        @Override
        public Codec<TransmutationRecipe> codec()
        {
            return CODEC;
        }

        @Override
        protected TransmutationRecipe makeRecipe(FriendlyByteBuf buffer, String group, NonNullList<Material> materials, ItemStack result, float experienceIn, int cookTimeIn)
        {
            return new TransmutationRecipe(group, materials, result, experienceIn, cookTimeIn);
        }
    }

    public static abstract class SerializerBase<T extends TransmutationRecipe>
            implements RecipeSerializer<T>
    {

        @Override
        public T fromNetwork(FriendlyByteBuf buffer)
        {
            String group = buffer.readUtf(32767);
            int numMaterials = buffer.readVarInt();
            NonNullList<Material> materials = NonNullList.create();
            for (int i = 0; i < numMaterials; i++)
            {
                materials.add(Material.read(buffer));
            }

            ItemStack result = buffer.readItem();
            float f = buffer.readFloat();
            int i = buffer.readVarInt();

            return makeRecipe(buffer, group, materials, result, f,i);
        }

        protected abstract T makeRecipe(FriendlyByteBuf buffer, String group, NonNullList<Material> materials, ItemStack result, float experienceIn, int cookTimeIn);

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TransmutationRecipe recipe)
        {
            buffer.writeUtf(recipe.group);
            buffer.writeVarInt(recipe.materials.size());
            for (Material input : recipe.materials)
            {
                input.write(buffer);
            }


            buffer.writeItem(recipe.output);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.cookTime);
        }
    }

    public static class Material implements Predicate<ItemStack>
    {
        public static final Codec<Material> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(mat -> mat.ingredient),
                Codec.INT.fieldOf("count").forGetter(mat -> mat.count)
        ).apply(instance, Material::new));

        public final Ingredient ingredient;
        public final int count;

        private Material(Ingredient ingredient, int count)
        {
            this.ingredient = ingredient;
            this.count = count;
        }

        public static Material of(Ingredient ingredient, int count)
        {
            return new Material(ingredient, count);
        }

        @Override
        public boolean test(ItemStack itemStack)
        {
            return ingredient.test(itemStack) && itemStack.getCount() >= count;
        }

        public void write(FriendlyByteBuf packet)
        {
            packet.writeVarInt(count);
            ingredient.toNetwork(packet);
        }

        public static Material read(FriendlyByteBuf packet)
        {
            int count = packet.readVarInt();
            Ingredient ingredient = Ingredient.fromNetwork(packet);
            return new Material(ingredient, count);
        }
    }
}
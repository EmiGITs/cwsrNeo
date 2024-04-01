package com.raptorbk.CyanWarriorSwordsRedux.customadv;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;


public class Somethingelsetrigger extends SimpleCriterionTrigger<Somethingelsetrigger.TriggerInstance> {

    public void trigger(ServerPlayer player) {
        this.trigger(player,

                triggerInstance -> true
        );
    }




    @Override
    public Codec<Somethingelsetrigger.TriggerInstance> codec() {
        return Somethingelsetrigger.TriggerInstance.CODEC;
    }


    public static record TriggerInstance() implements CriterionTriggerInstance, SimpleInstance {
        public static final Codec<Somethingelsetrigger.TriggerInstance> CODEC = Codec.unit(new Somethingelsetrigger.TriggerInstance());

        @Override
        public void validate(@NotNull CriterionValidator p_312237_) {
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }
}
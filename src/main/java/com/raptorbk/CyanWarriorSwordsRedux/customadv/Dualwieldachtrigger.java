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


public class Dualwieldachtrigger extends SimpleCriterionTrigger<Dualwieldachtrigger.TriggerInstance> {

    public void trigger(ServerPlayer player) {
        this.trigger(player,

                triggerInstance -> true
        );
    }




    @Override
    public Codec<Dualwieldachtrigger.TriggerInstance> codec() {
        return Dualwieldachtrigger.TriggerInstance.CODEC;
    }


    public static record TriggerInstance() implements CriterionTriggerInstance, SimpleInstance {
        public static final Codec<Dualwieldachtrigger.TriggerInstance> CODEC = Codec.unit(new Dualwieldachtrigger.TriggerInstance());

        @Override
        public void validate(@NotNull CriterionValidator p_312237_) {
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }
}
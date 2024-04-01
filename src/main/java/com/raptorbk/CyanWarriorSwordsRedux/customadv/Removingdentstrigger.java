package com.raptorbk.CyanWarriorSwordsRedux.customadv;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;


public class Removingdentstrigger extends SimpleCriterionTrigger<Removingdentstrigger.TriggerInstance> {

    public void trigger(ServerPlayer player) {
        this.trigger(player,

                triggerInstance -> true
        );
    }




    @Override
    public Codec<Removingdentstrigger.TriggerInstance> codec() {
        return Removingdentstrigger.TriggerInstance.CODEC;
    }


    public static record TriggerInstance() implements CriterionTriggerInstance, SimpleInstance {
        public static final Codec<Removingdentstrigger.TriggerInstance> CODEC = Codec.unit(new Removingdentstrigger.TriggerInstance());

        @Override
        public void validate(@NotNull CriterionValidator p_312237_) {
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }
}
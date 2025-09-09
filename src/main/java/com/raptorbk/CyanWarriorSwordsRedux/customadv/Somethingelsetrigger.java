package com.raptorbk.CyanWarriorSwordsRedux.customadv;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import javax.annotation.Nonnull;

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


    public static record TriggerInstance() implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<Somethingelsetrigger.TriggerInstance> CODEC = Codec.unit(new Somethingelsetrigger.TriggerInstance());

        @Override
        public void validate(@Nonnull CriterionValidator p_312237_) {
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }
}
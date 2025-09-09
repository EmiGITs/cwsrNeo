package com.raptorbk.CyanWarriorSwordsRedux.customadv;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import javax.annotation.Nonnull;

import java.util.Optional;


public class Bestbothtrigger extends SimpleCriterionTrigger<Bestbothtrigger.TriggerInstance> {

    public void trigger(ServerPlayer player) {
        this.trigger(player,

                triggerInstance -> true
        );
    }




    @Override
    public Codec<Bestbothtrigger.TriggerInstance> codec() {
        return Bestbothtrigger.TriggerInstance.CODEC;
    }


    public static record TriggerInstance() implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<Bestbothtrigger.TriggerInstance> CODEC = Codec.unit(new Bestbothtrigger.TriggerInstance());

        @Override
        public void validate(@Nonnull CriterionValidator p_312237_) {
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }
}
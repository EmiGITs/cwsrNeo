package com.raptorbk.CyanWarriorSwordsRedux.customadv;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import javax.annotation.Nonnull;

import java.util.Optional;

public class Sworddestroyedtrigger extends SimpleCriterionTrigger<Sworddestroyedtrigger.TriggerInstance> {

    public void trigger(ServerPlayer player) {
        this.trigger(player,

                triggerInstance -> true
        );
    }




    @Override
    public Codec<TriggerInstance> codec() {
        return Sworddestroyedtrigger.TriggerInstance.CODEC;
    }


    public static record TriggerInstance() implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<Sworddestroyedtrigger.TriggerInstance> CODEC = Codec.unit(new Sworddestroyedtrigger.TriggerInstance());

        @Override
        public void validate(@Nonnull CriterionValidator p_312237_) {
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }
}

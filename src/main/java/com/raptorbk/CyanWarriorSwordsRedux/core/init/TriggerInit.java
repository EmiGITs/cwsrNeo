package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.customadv.*;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TriggerInit {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, CyanWarriorSwordsRedux.MOD_ID);
    public static final DeferredHolder<CriterionTrigger<?>, Dualwieldachtrigger> DWE_TRIGGER= TRIGGERS.register("dual_wield_ach_trigger", Dualwieldachtrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, Sworddestroyedtrigger> SD_TRIGGER= TRIGGERS.register("sword_destroyed_trigger", Sworddestroyedtrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, Reallyradtrigger> RR_TRIGGER= TRIGGERS.register("really_rad_trigger", Reallyradtrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, Somethingelsetrigger> SE_TRIGGER= TRIGGERS.register("something_else_trigger", Somethingelsetrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, Themoretrigger> TM_TRIGGER= TRIGGERS.register("the_more_trigger", Themoretrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, Ablasttrigger> AB_TRIGGER= TRIGGERS.register("a_blast_trigger", Ablasttrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, Removingdentstrigger> RD_TRIGGER= TRIGGERS.register("removing_dents_trigger", Removingdentstrigger::new);

    public static final DeferredHolder<CriterionTrigger<?>, Bestbothtrigger> BB_TRIGGER= TRIGGERS.register("best_both_trigger", Bestbothtrigger::new);


}

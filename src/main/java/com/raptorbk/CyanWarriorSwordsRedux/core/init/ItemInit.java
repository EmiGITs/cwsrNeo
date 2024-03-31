package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.FireType.FIRE_SWORD;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemInit {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CyanWarriorSwordsRedux.MOD_ID);

    public static final DeferredItem<Item> SYNERGY_TOTEM = ITEMS.register("synergy_totem", () -> new SYNERGY_TOTEM(new Item.Properties()));

    public static final DeferredItem<Item> ACTIVE_SYNERGY_TOTEM = ITEMS.register("active_synergy_totem", () -> new ACTIVE_SYNERGY_TOTEM(new Item.Properties()));

    public static final DeferredItem<Item> FIRE_SWORD = ITEMS.register("fire_sword", () -> new FIRE_SWORD(TierInit.SWORD, 8, -2.6f, new Item.Properties().stacksTo(1)));
}

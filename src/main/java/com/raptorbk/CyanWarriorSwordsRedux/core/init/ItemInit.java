package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.Totems.ABILITY_TOTEM;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.Totems.ACTIVE_SYNERGY_TOTEM;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.Totems.SYNERGY_TOTEM;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.BeastType.BEAST_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.BeastType.GOLEM_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.CyanType.CYAN_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.DarkType.DARK_NETHER;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.FireType.FIRE_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.ThunderType.THUNDER_SWORD;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemInit {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CyanWarriorSwordsRedux.MOD_ID);

    public static final DeferredItem<Item> SYNERGY_TOTEM = ITEMS.register("synergy_totem", () -> new SYNERGY_TOTEM(new Item.Properties()));

    public static final DeferredItem<Item> ACTIVE_SYNERGY_TOTEM = ITEMS.register("active_synergy_totem", () -> new ACTIVE_SYNERGY_TOTEM(new Item.Properties()));

    public static final DeferredItem<Item> ABILITY_TOTEM = ITEMS.register("ability_totem", () -> new ABILITY_TOTEM(new Item.Properties()));
    public static final DeferredItem<Item> FIRE_SWORD = ITEMS.register("fire_sword", () -> new FIRE_SWORD( -2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> THUNDER_SWORD = ITEMS.register("thunder_sword", () -> new THUNDER_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CYAN_SWORD = ITEMS.register("cyan_sword", () -> new CYAN_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> BEAST_SWORD = ITEMS.register("beast_sword", () -> new BEAST_SWORD(-2.4f, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GOLEM_SWORD = ITEMS.register("golem_sword", () -> new GOLEM_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> DARK_NETHER = ITEMS.register("dark_nether", () -> new DARK_NETHER(-2.4f, new Item.Properties().stacksTo(1)));

}

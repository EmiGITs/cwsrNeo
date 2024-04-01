package com.raptorbk.CyanWarriorSwordsRedux.core.init;

import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.Totems.ABILITY_TOTEM;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.Totems.ACTIVE_SYNERGY_TOTEM;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.Totems.SYNERGY_TOTEM;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.BeastType.BEAST_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.BeastType.GOLEM_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.CyanType.CYAN_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.DarkType.DARK_NETHER;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.DarkType.DARK_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.EarthType.EARTH_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.EarthType.WILD_NATURE;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.EnderType.ENDER_PORTAL;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.EnderType.ENDER_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.FireType.COMBUSTION_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.FireType.FIRE_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.FireType.METEOR_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.LightType.LIGHT_NETHER;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.LightType.LIGHT_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.Mixing.*;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.ThunderType.THUNDER_SHOCK;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.ThunderType.THUNDER_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.WaterType.ICE_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.WaterType.WATER_SWORD;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.WindType.WIND_IMPULSE;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.swords.WindType.WIND_SWORD;
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

    public static final DeferredItem<Item> DARK_SWORD = ITEMS.register("dark_sword", () -> new DARK_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> EARTH_SWORD = ITEMS.register("earth_sword", () -> new EARTH_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> WILD_NATURE = ITEMS.register("wild_nature", () -> new WILD_NATURE(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> WATER_SWORD = ITEMS.register("water_sword", () -> new WATER_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> METEOR_SWORD = ITEMS.register("meteor_sword", () -> new METEOR_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> METEORIC_THUNDERSTORM = ITEMS.register("meteoric_thunderstorm", () -> new METEORIC_THUNDERSTORM(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> ENDER_WIND = ITEMS.register("ender_wind", () -> new ENDER_WIND(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> ENDER_PORTAL = ITEMS.register("ender_portal", () -> new ENDER_PORTAL(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> ENDER_SWORD = ITEMS.register("ender_sword", () -> new ENDER_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> COMBUSTION_SWORD = ITEMS.register("combustion_sword", () -> new COMBUSTION_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> LIGHT_SWORD = ITEMS.register("light_sword", () -> new LIGHT_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> LIGHT_NETHER = ITEMS.register("light_nether", () -> new LIGHT_NETHER(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> ATLANTIS_SWORD = ITEMS.register("atlantis_sword", () -> new ATLANTIS_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> ENDER_FIRE = ITEMS.register("ender_fire", () -> new ENDER_FIRE(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> ENDER_THUNDER = ITEMS.register("ender_thunder", () -> new ENDER_THUNDER(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> PEACEFUL_NATURE = ITEMS.register("peaceful_nature", () -> new PEACEFUL_NATURE(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> STEAM_SWORD = ITEMS.register("steam_sword", () -> new STEAM_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> THUNDERSTORM_SWORD = ITEMS.register("thunderstorm_sword", () -> new THUNDERSTORM_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> TIME_SWORD = ITEMS.register("time_sword", () -> new TIME_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> TRI_ENDER = ITEMS.register("tri_ender", () -> new TRI_ENDER(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> WIND_BLAST = ITEMS.register("wind_blast", () -> new WIND_BLAST(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> WIND_BOOM = ITEMS.register("wind_boom", () -> new WIND_BOOM(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> THUNDER_SHOCK = ITEMS.register("thunder_shock", () -> new THUNDER_SHOCK(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> ICE_SWORD = ITEMS.register("ice_sword", () -> new ICE_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> WIND_IMPULSE = ITEMS.register("wind_impulse", () -> new WIND_IMPULSE(-2.4f, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> WIND_SWORD = ITEMS.register("wind_sword", () -> new WIND_SWORD(-2.4f, new Item.Properties().stacksTo(1)));

}

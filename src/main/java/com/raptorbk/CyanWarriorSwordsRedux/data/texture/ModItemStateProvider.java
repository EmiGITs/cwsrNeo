package com.raptorbk.CyanWarriorSwordsRedux.data.texture;

import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.core.init.ItemInit;
import com.raptorbk.CyanWarriorSwordsRedux.util.NameUtility;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemStateProvider extends ItemModelProvider {
    public ModItemStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CyanWarriorSwordsRedux.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        item(ItemInit.FIRE_SWORD.get());
        item(ItemInit.THUNDER_SWORD.get());
        item(ItemInit.CYAN_SWORD.get());
        item(ItemInit.SYNERGY_TOTEM.get());
        item(ItemInit.ACTIVE_SYNERGY_TOTEM.get());
        item(ItemInit.ABILITY_TOTEM.get());
        item(ItemInit.BEAST_SWORD.get());
        item(ItemInit.GOLEM_SWORD.get());
        item(ItemInit.DARK_NETHER.get());
        item(ItemInit.DARK_SWORD.get());
        item(ItemInit.EARTH_SWORD.get());
        item(ItemInit.WILD_NATURE.get());
        item(ItemInit.WATER_SWORD.get());
        item(ItemInit.METEOR_SWORD.get());
        item(ItemInit.METEORIC_THUNDERSTORM.get());
        item(ItemInit.ENDER_WIND.get());
        item(ItemInit.ENDER_PORTAL.get());
        item(ItemInit.ENDER_SWORD.get());
        item(ItemInit.COMBUSTION_SWORD.get());
        item(ItemInit.LIGHT_NETHER.get());
        item(ItemInit.LIGHT_SWORD.get());
        item(ItemInit.ATLANTIS_SWORD.get());
        item(ItemInit.ENDER_FIRE.get());
        item(ItemInit.ENDER_THUNDER.get());
        item(ItemInit.PEACEFUL_NATURE.get());
        item(ItemInit.STEAM_SWORD.get());
        item(ItemInit.THUNDERSTORM_SWORD.get());
        item(ItemInit.TIME_SWORD.get());
        item(ItemInit.TRI_ENDER.get());
        item(ItemInit.WIND_BLAST.get());
        item(ItemInit.WIND_BOOM.get());
        item(ItemInit.THUNDER_SHOCK.get());
        item(ItemInit.ICE_SWORD.get());
        item(ItemInit.WIND_IMPULSE.get());
        item(ItemInit.WIND_SWORD.get());

        //ITEMS

        item(ItemInit.SWORD_HANDLE.get());

        //ESSENCE
        item(ItemInit.BEAST_ESSENCE.get());
        item(ItemInit.DARK_ESSENCE.get());
        item(ItemInit.EARTH_ESSENCE.get());
        item(ItemInit.ENDER_ESSENCE.get());
        item(ItemInit.FIRE_ESSENCE.get());
        item(ItemInit.LIGHT_ESSENCE.get());
        item(ItemInit.MIXED_ESSENCE.get());
        item(ItemInit.THUNDER_ESSENCE.get());
        item(ItemInit.WATER_ESSENCE.get());
        item(ItemInit.WIND_ESSENCE.get());



    }


    private void item(Item item) {
        String name = NameUtility.getItemName(item);
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", "item/" + name);
    }
}
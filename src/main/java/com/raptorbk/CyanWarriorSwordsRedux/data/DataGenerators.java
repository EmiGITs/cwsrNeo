package com.raptorbk.CyanWarriorSwordsRedux.data;


import com.raptorbk.CyanWarriorSwordsRedux.CyanWarriorSwordsRedux;
import com.raptorbk.CyanWarriorSwordsRedux.data.texture.ModItemStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {

    public static void gatherData(GatherDataEvent event) {
        try {
            DataGenerator generator = event.getGenerator();
            PackOutput output = event.getGenerator().getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            generator.addProvider(true, new ModItemStateProvider(output, existingFileHelper));
        } catch (RuntimeException e) {
            CyanWarriorSwordsRedux.logger.error("Failed to generate data", e);
        }
    }
}
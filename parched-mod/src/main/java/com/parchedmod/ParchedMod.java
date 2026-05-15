package com.parchedmod;

import com.parchedmod.init.ModEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.BiomeKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParchedMod implements ModInitializer {

    public static final String MOD_ID = "parched";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initialising Parched Skeletons mod...");

        // 1. Register entity type + attributes
        ModEntities.register();

        // 2. Spawn in desert biomes — weight 75 means parched replaces ~50% of skeletons
        //    (vanilla skeleton weight is also 80 in deserts, so this ratio is close to vanilla 1.21.11)
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(BiomeKeys.DESERT),
                SpawnGroup.MONSTER,
                ModEntities.PARCHED,
                75,   // weight
                2,    // min group size
                4     // max group size
        );

        LOGGER.info("Parched Skeletons loaded!");
    }
}

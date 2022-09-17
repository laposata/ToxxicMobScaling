package com.dreamtea;

import com.dreamtea.mobs.LootTablesUpdated;
import com.dreamtea.structures.EndShipLootShulker;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DatagenEntry implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(LootTablesUpdated::new);
        fabricDataGenerator.addProvider(EndShipLootShulker::new);
    }
}
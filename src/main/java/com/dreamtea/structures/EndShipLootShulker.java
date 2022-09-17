package com.dreamtea.structures;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.EnchantWithLevelsLootFunction;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetNbtLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

import static com.dreamtea.MobScaling.NAMESPACE;

public class EndShipLootShulker extends SimpleFabricLootTableProvider {
  public static final NbtCompound FLIGHT1;
  public static final NbtCompound FLIGHT2;
  public static final NbtCompound FLIGHT3;
  public static final NbtCompound FLIGHT4;
  public static final NbtCompound FLIGHT5;
  public EndShipLootShulker(FabricDataGenerator dataGenerator) {
    super(dataGenerator, LootContextTypes.CHEST);
  }


  @Override
  public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
    biConsumer.accept(new Identifier(NAMESPACE, "chests/end_ship_rockets"),
      LootTable.builder()
        .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(22f, 27f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.FIREWORK_ROCKET).weight(10))
            .apply(SetNbtLootFunction.builder(FLIGHT1))
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(34f, 64f)))))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.FIREWORK_ROCKET).weight(25))
            .apply(SetNbtLootFunction.builder(FLIGHT2))
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(34f, 64f)))))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.FIREWORK_ROCKET).weight(20))
            .apply(SetNbtLootFunction.builder(FLIGHT3))
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(34f, 64f)))))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.FIREWORK_ROCKET).weight(5))
            .apply(SetNbtLootFunction.builder(FLIGHT3))
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(34f, 64f)))))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.FIREWORK_ROCKET).weight(1))
            .apply(SetNbtLootFunction.builder(FLIGHT3))
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(34f, 64f)))))
        ));

  }

  static {
    FLIGHT1 = new ItemStack(Items.FIREWORK_ROCKET).getOrCreateSubNbt("Fireworks");
    FLIGHT2 = FLIGHT1.copy();
    FLIGHT3 = FLIGHT1.copy();
    FLIGHT4 = FLIGHT1.copy();
    FLIGHT5 = FLIGHT1.copy();
    FLIGHT1.putByte("Flight", (byte)1);
    FLIGHT2.putByte("Flight", (byte)2);
    FLIGHT3.putByte("Flight", (byte)3);
    FLIGHT4.putByte("Flight", (byte)4);
    FLIGHT5.putByte("Flight", (byte)5);
  }
}

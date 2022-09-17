package com.dreamtea.mobs;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.FrogVariant;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.entry.TagEntry;
import net.minecraft.loot.function.FurnaceSmeltLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetPotionLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.potion.Potions;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.SlimePredicate;
import net.minecraft.predicate.entity.TypeSpecificPredicate;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LootTablesUpdated extends SimpleFabricLootTableProvider {

  private final Map<Identifier, LootTable.Builder> lootTables = Maps.newHashMap();

  private static final EntityPredicate.Builder NEEDS_ENTITY_ON_FIRE = EntityPredicate.Builder.create().flags(EntityFlagsPredicate.Builder.create().onFire(true).build());
  private static final Set<EntityType<?>> ENTITY_TYPES_IN_MISC_GROUP_TO_CHECK = ImmutableSet.of(EntityType.PLAYER, EntityType.ARMOR_STAND, EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM, EntityType.VILLAGER);

  public LootTablesUpdated(FabricDataGenerator dataGenerator) {
    super(dataGenerator, LootContextTypes.ENTITY);
  }

  @Override
  public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
    this.register(EntityType.BLAZE, 
      LootTable.builder()
        .pool(LootPool.builder()
          .rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder) ItemEntry.builder(Items.BLAZE_ROD)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 4f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f)))))
            .conditionally(KilledByPlayerLootCondition.builder())));

    this.register(EntityType.CAVE_SPIDER, 
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.STRING)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
            .conditionally(KilledByPlayerLootCondition.builder().invert()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(3.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.STRING)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.SPIDER_EYE)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-1.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
            .conditionally(KilledByPlayerLootCondition.builder())));

    this.register(EntityType.CREEPER,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.GUNPOWDER)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.GUNPOWDER)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder()
          .with(TagEntry.expandBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS))
              .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.create().type(EntityTypeTags.SKELETONS)))));

    this.register(EntityType.DROWNED,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ROTTEN_FLESH)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ROTTEN_FLESH)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with(ItemEntry.builder(Items.COPPER_INGOT))
            .conditionally(KilledByPlayerLootCondition.builder())
            .conditionally(RandomChanceWithLootingLootCondition.builder(0.11f, 0.02f))));

    this.register(EntityType.ELDER_GUARDIAN,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.PRISMARINE_SHARD)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)((LeafEntry.Builder)ItemEntry.builder(Items.COD).weight(3))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
            .apply((LootFunction.Builder)((Object)FurnaceSmeltLootFunction.builder()
              .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, NEEDS_ENTITY_ON_FIRE))))))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.PRISMARINE_CRYSTALS).weight(2))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .with(EmptyEntry.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(16.0f))
          .with(ItemEntry.builder(Blocks.WET_SPONGE))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1f))
          .with(ItemEntry.builder(Items.TRIDENT).weight(1))
          .with(ItemEntry.builder(Items.HEART_OF_THE_SEA).weight(4))
          .with(ItemEntry.builder(Items.NAUTILUS_SHELL).weight(10)
            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4f))))
          .with(EmptyEntry.builder().weight(5))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object) LootTableEntry.builder(LootTables.FISHING_FISH_GAMEPLAY)
            .apply((LootFunction.Builder)((Object)FurnaceSmeltLootFunction.builder()
              .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, NEEDS_ENTITY_ON_FIRE))))))
          .conditionally(KilledByPlayerLootCondition.builder())
          .conditionally(RandomChanceWithLootingLootCondition.builder(0.025f, 0.01f))));

    this.register(EntityType.ENDER_DRAGON,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1f))
          .with(ItemEntry.builder(Blocks.DRAGON_EGG))
          .conditionally(KilledByPlayerLootCondition.builder())));

    this.register(EntityType.ENDERMAN,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(4.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ENDER_PEARL)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))));

    this.register(EntityType.ENDERMITE,
      LootTable.builder());

    this.register(EntityType.EVOKER,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0f))
          .with(ItemEntry.builder(Items.TOTEM_OF_UNDYING)))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.EMERALD)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder())));

    this.register(EntityType.GHAST,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(7.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.GHAST_TEAR)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(7.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.GUNPOWDER)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.GHAST_TEAR)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.GUNPOWDER)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))));

    this.register(EntityType.GUARDIAN,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.PRISMARINE_SHARD)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.PRISMARINE_SHARD)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)((LeafEntry.Builder)ItemEntry.builder(Items.COD).weight(2))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
            .apply((LootFunction.Builder)((Object)FurnaceSmeltLootFunction.builder()
              .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, NEEDS_ENTITY_ON_FIRE))))))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.PRISMARINE_CRYSTALS).weight(2))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .with(EmptyEntry.builder())).pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)LootTableEntry.builder(LootTables.FISHING_FISH_GAMEPLAY)
            .apply((LootFunction.Builder)((Object)FurnaceSmeltLootFunction.builder()
              .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, NEEDS_ENTITY_ON_FIRE))))))
          .conditionally(KilledByPlayerLootCondition.builder())
          .conditionally(RandomChanceWithLootingLootCondition.builder(0.05f, 0.04f))));

    this.register(EntityType.HUSK,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ROTTEN_FLESH)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ROTTEN_FLESH)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0f))
          .with(ItemEntry.builder(Items.IRON_INGOT))
          .with(ItemEntry.builder(Items.CARROT))
          .with((LootPoolEntry.Builder<?>)((Object)ItemEntry.builder(Items.POTATO)
            .apply((LootFunction.Builder)((Object)FurnaceSmeltLootFunction.builder()
              .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, NEEDS_ENTITY_ON_FIRE))))))
          .conditionally(KilledByPlayerLootCondition.builder())
          .conditionally(RandomChanceWithLootingLootCondition.builder(0.025f, 0.01f))));

    this.register(EntityType.RAVAGER,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with(ItemEntry.builder(Items.TOTEM_OF_UNDYING)))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)ItemEntry.builder(Items.SADDLE)
            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))))));

    this.register(EntityType.ILLUSIONER,
      LootTable.builder());

    this.register(EntityType.IRON_GOLEM,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)ItemEntry.builder(Blocks.POPPY)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(7.0f))
          .with((LootPoolEntry.Builder<?>)((Object)ItemEntry.builder(Items.IRON_INGOT)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0f, 5.0f)))))));

    this.register(EntityType.MAGMA_CUBE,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(3.0f))
          .with((LootPoolEntry.Builder<?>)((LeafEntry.Builder)((LootPoolEntry.Builder)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.MAGMA_CREAM)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-2.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
            .conditionally(this.killedByFrog().invert())).conditionally(KilledByPlayerLootCondition.builder())))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((LeafEntry.Builder)((LootPoolEntry.Builder)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.MAGMA_CREAM)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-2.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
            .conditionally(this.killedByFrog().invert()))
            .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(SlimePredicate.of(NumberRange.IntRange.atLeast(2))))))
          .with((LootPoolEntry.Builder<?>)((LootPoolEntry.Builder)((Object)ItemEntry.builder(Items.PEARLESCENT_FROGLIGHT)
            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))))
            .conditionally(this.killedByFrog(FrogVariant.WARM)))
          .with((LootPoolEntry.Builder<?>)((LootPoolEntry.Builder)((Object)ItemEntry.builder(Items.VERDANT_FROGLIGHT)
            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))))
            .conditionally(this.killedByFrog(FrogVariant.COLD)))
          .with((LootPoolEntry.Builder<?>)((LootPoolEntry.Builder)((Object)ItemEntry.builder(Items.OCHRE_FROGLIGHT)
            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))))
            .conditionally(this.killedByFrog(FrogVariant.TEMPERATE)))));

    this.register(EntityType.PHANTOM,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.PHANTOM_MEMBRANE)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder())));

    this.register(EntityType.PILLAGER,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Blocks.EMERALD_BLOCK)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-9f, 1f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, .5f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.EMERALD)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 8.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 4.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder())));


    this.register(EntityType.PUFFERFISH,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)ItemEntry.builder(Items.PUFFERFISH)
            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with(ItemEntry.builder(Items.BONE_MEAL).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0f, 16f))))
          .conditionally(KilledByPlayerLootCondition.builder())));

    this.register(EntityType.SHULKER,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with(ItemEntry.builder(Items.SHULKER_SHELL))
          .conditionally(RandomChanceWithLootingLootCondition.builder(0.5f, 0.0625f))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with(ItemEntry.builder(Items.SHULKER_SHELL))
          .conditionally(KilledByPlayerLootCondition.builder())));

    this.register(EntityType.SILVERFISH,
      LootTable.builder());

    this.register(EntityType.SKELETON,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ARROW)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.BONE)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ARROW)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.BONE)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))));

    this.register(EntityType.SKELETON_HORSE,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.BONE)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))));

    this.register(EntityType.SLIME,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0f))
          .with((LootPoolEntry.Builder<?>)((LootPoolEntry.Builder)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.SLIME_BALL)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((LootPoolEntry.Builder)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.SLIME_BALL)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
            .conditionally(this.killedByFrog().invert()))
          .with((LootPoolEntry.Builder<?>)((LootPoolEntry.Builder)((Object)ItemEntry.builder(Items.SLIME_BALL)
            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))))
            .conditionally(this.killedByFrog()))
          .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(SlimePredicate.of(NumberRange.IntRange.exactly(1)))))));

    this.register(EntityType.SPIDER,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.STRING)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder().invert()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.STRING)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.SPIDER_EYE)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-1.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder())));

    this.register(EntityType.STRAY,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ARROW)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.BONE)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ARROW)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.BONE)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))).pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)((LeafEntry.Builder)ItemEntry.builder(Items.TIPPED_ARROW)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))
              .withLimit(1)))
            .apply(SetPotionLootFunction.builder(Potions.SLOWNESS)))).conditionally(KilledByPlayerLootCondition.builder())));

    this.register(EntityType.WARDEN,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with(ItemEntry.builder(Items.SCULK_CATALYST))));

    this.register(EntityType.VINDICATOR,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.EMERALD)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder())));

    this.register(EntityType.WITCH,
      LootTable.builder()
        .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(2.0f, 6.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.GLOWSTONE_DUST)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.SUGAR)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.REDSTONE)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.SPIDER_EYE)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.GLASS_BOTTLE)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.GUNPOWDER)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)((LeafEntry.Builder)ItemEntry.builder(Items.STICK).weight(2))
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))));

    this.register(EntityType.WITHER,
      LootTable.builder());

    this.register(EntityType.WITHER_SKELETON,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.COAL)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-1.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.BONE)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with(ItemEntry.builder(Blocks.WITHER_SKELETON_SKULL))
          .conditionally(KilledByPlayerLootCondition.builder())
          .conditionally(RandomChanceWithLootingLootCondition.builder(0.1f, 0.05f))));

    this.register(EntityType.ZOGLIN,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with(ItemEntry.builder(Items.TOTEM_OF_UNDYING))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ROTTEN_FLESH)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))));

    this.register(EntityType.ZOMBIE,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ROTTEN_FLESH)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ROTTEN_FLESH)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0f))
          .with(ItemEntry.builder(Items.IRON_INGOT))
          .with(ItemEntry.builder(Items.CARROT))
          .with((LootPoolEntry.Builder<?>)((Object)ItemEntry.builder(Items.POTATO)
            .apply((LootFunction.Builder)((Object)FurnaceSmeltLootFunction.builder()
              .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, NEEDS_ENTITY_ON_FIRE))))))
          .conditionally(KilledByPlayerLootCondition.builder()).conditionally(RandomChanceWithLootingLootCondition.builder(0.025f, 0.01f))));

    this.register(EntityType.ZOMBIE_HORSE,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ROTTEN_FLESH)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))));

    this.register(EntityType.ZOMBIFIED_PIGLIN,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ROTTEN_FLESH)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ROTTEN_FLESH)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.GOLD_NUGGET)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with(ItemEntry.builder(Items.GOLD_INGOT))
          .conditionally(KilledByPlayerLootCondition.builder())
          .conditionally(RandomChanceWithLootingLootCondition.builder(0.05f, 0.05f))));

    this.register(EntityType.HOGLIN,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)((LeafEntry.Builder)ItemEntry.builder(Items.PORKCHOP)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 4.0f))))
            .apply((LootFunction.Builder)((Object)FurnaceSmeltLootFunction.builder()
              .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, NEEDS_ENTITY_ON_FIRE)))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.LEATHER)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)((LeafEntry.Builder)ItemEntry.builder(Items.PORKCHOP)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 4.0f))))
            .apply((LootFunction.Builder)((Object)FurnaceSmeltLootFunction.builder()
              .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, NEEDS_ENTITY_ON_FIRE)))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.LEATHER)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))));

    this.register(EntityType.PIGLIN,
      LootTable.builder());

    this.register(EntityType.PIGLIN_BRUTE,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with(ItemEntry.builder(Items.ANCIENT_DEBRIS))
          .conditionally(KilledByPlayerLootCondition.builder())
          .conditionally(RandomChanceWithLootingLootCondition.builder(0.1f, 0.05f))));

    this.register(EntityType.ZOMBIE_VILLAGER,
      LootTable.builder()
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ROTTEN_FLESH)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
          .with((LootPoolEntry.Builder<?>)((Object)((LeafEntry.Builder)ItemEntry.builder(Items.ROTTEN_FLESH)
            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))))
          .conditionally(KilledByPlayerLootCondition.builder()))
        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(2.0f))
          .with(ItemEntry.builder(Items.IRON_INGOT))
          .with(ItemEntry.builder(Items.CARROT))
          .with((LootPoolEntry.Builder<?>)((Object)ItemEntry.builder(Items.POTATO)
            .apply((LootFunction.Builder)((Object)FurnaceSmeltLootFunction.builder()
              .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, NEEDS_ENTITY_ON_FIRE))))))
          .conditionally(KilledByPlayerLootCondition.builder()).conditionally(RandomChanceWithLootingLootCondition.builder(0.025f, 0.01f))));


    this.lootTables.forEach(biConsumer);
  }

  private void register(EntityType<?> entityType, LootTable.Builder lootTable) {
    lootTable = lootTable.pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
        .with(ItemEntry.builder(Items.TOTEM_OF_UNDYING))
        .conditionally(KilledByPlayerLootCondition.builder())
        .conditionally(RandomChanceWithLootingLootCondition.builder(0.005f, 0.002f)));
    this.register(entityType.getLootTableId(), lootTable);
  }
  private LootCondition.Builder killedByFrog() {
    return DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create().sourceEntity(EntityPredicate.Builder.create().type(EntityType.FROG)));
  }

  private LootCondition.Builder killedByFrog(FrogVariant variant) {
    return DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create().sourceEntity(EntityPredicate.Builder.create().type(EntityType.FROG).typeSpecific(TypeSpecificPredicate.frog(variant))));
  }

  private void register(Identifier entityId, LootTable.Builder lootTable) {
    this.lootTables.put(entityId, lootTable);
  }

  @Override
  public String getName() {
    return "Enraged Enemies Boosted Loot";
  }
}

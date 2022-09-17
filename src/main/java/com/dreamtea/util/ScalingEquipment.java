package com.dreamtea.util;

import com.dreamtea.imixin.IGetEquipment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.math.random.Random;

import java.util.Map;

public class ScalingEquipment {

  static final Map<Class<? extends ToolItem>, Integer> WEAPON_TYPES = Map.of(
    SwordItem.class, 10,
    AxeItem.class, 5,
    ShovelItem.class, 10,
    PickaxeItem.class, 2,
    HoeItem.class, 2
  );

  static final Map<ArmorMaterial, Integer> ARMOR_LEVELS = Map.of(
    ArmorMaterials.LEATHER, 20,
    ArmorMaterials.GOLD, 12,
    ArmorMaterials.CHAIN, 12,
    ArmorMaterials.IRON, 12,
    ArmorMaterials.DIAMOND, 6,
    ArmorMaterials.NETHERITE, 1);

  static final Map<ToolMaterial, Integer> TOOL_LEVELS = Map.of(
    ToolMaterials.WOOD, 10,
    ToolMaterials.STONE, 10,
    ToolMaterials.GOLD, 20,
    ToolMaterials.IRON, 30,
    ToolMaterials.DIAMOND, 5,
    ToolMaterials.NETHERITE, 1);

  static final Map<ToolMaterial, ToolMaterial> NEXT_TOOL_TIER = Map.of(
    ToolMaterials.WOOD, ToolMaterials.STONE,
    ToolMaterials.STONE, ToolMaterials.GOLD,
    ToolMaterials.GOLD, ToolMaterials.IRON,
    ToolMaterials.IRON, ToolMaterials.DIAMOND,
    ToolMaterials.DIAMOND, ToolMaterials.NETHERITE
  );

  static final Map<ArmorMaterial, ArmorMaterial> NEXT_ARMOR_TIER = Map.of(
    ArmorMaterials.LEATHER, ArmorMaterials.CHAIN,
    ArmorMaterials.CHAIN, ArmorMaterials.GOLD,
    ArmorMaterials.GOLD, ArmorMaterials.IRON,
    ArmorMaterials.IRON, ArmorMaterials.DIAMOND,
    ArmorMaterials.DIAMOND, ArmorMaterials.NETHERITE
  );

  public static ToolMaterial getNextToolMaterial(ToolMaterial material){
    return NEXT_TOOL_TIER.getOrDefault(material, material);
  }

  public static ArmorMaterial getNextArmorMaterial(ArmorMaterial material){
    return NEXT_ARMOR_TIER.getOrDefault(material, material);
  }

  public static ToolItem getTool(ToolMaterial material, Class<? extends ToolItem> type){
    return ((IGetEquipment<ToolItem, Class<? extends ToolItem>>)material).getEquipment(type);
  }

  public static ArmorItem getArmor(ArmorMaterial material, EquipmentSlot type){
    return ((IGetEquipment<ArmorItem, EquipmentSlot>)material).getEquipment(type);
  }

  public static ArmorItem getRandomArmor(Random r, EquipmentSlot slot){
    return getArmor(RandomSelector.getFromWeighted(ARMOR_LEVELS, r), slot);
  }

  public static ToolItem getRandomTool(Random r){
    return getTool(RandomSelector.getFromWeighted(TOOL_LEVELS, r), RandomSelector.getFromWeighted(WEAPON_TYPES, r));
  }
}

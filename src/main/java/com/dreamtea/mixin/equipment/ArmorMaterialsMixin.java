package com.dreamtea.mixin.equipment;

import com.dreamtea.imixin.IGetEquipment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import org.spongepowered.asm.mixin.Mixin;

import java.util.HashMap;
import java.util.Map;

@Mixin(ArmorMaterials.class)
public class ArmorMaterialsMixin implements IGetEquipment<ArmorItem, EquipmentSlot> {

  private final Map<EquipmentSlot, ArmorItem> armorItemMap = new HashMap<>();

  @Override
  public ArmorItem getEquipment(EquipmentSlot type) {
    return armorItemMap.get(type);
  }

  @Override
  public void registerEquipment(ArmorItem item, EquipmentSlot type) {
    armorItemMap.put(type, item);
  }
}

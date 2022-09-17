package com.dreamtea.imixin;

import net.minecraft.item.Item;

public interface IGetEquipment<I, T> {
  I getEquipment(T type);

  void registerEquipment(I item, T type);

}

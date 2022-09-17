package com.dreamtea.mixin.equipment;

import com.dreamtea.imixin.IGetEquipment;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Mixin;

import java.util.HashMap;
import java.util.Map;

@Mixin(ToolMaterials.class)
public class ToolMaterialsMixin implements IGetEquipment<ToolItem, Class<? extends ToolItem>> {

  private final Map<Class<? extends ToolItem>, ToolItem> tools = new HashMap<>();

  @Override
  public ToolItem getEquipment(Class<? extends ToolItem> type) {
    return tools.get(type);
  }

  @Override
  public void registerEquipment(ToolItem item, Class<? extends ToolItem> type) {
    tools.put(type, item);
  }
}

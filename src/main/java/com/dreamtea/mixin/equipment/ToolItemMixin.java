package com.dreamtea.mixin.equipment;

import com.dreamtea.imixin.IGetEquipment;
import com.dreamtea.imixin.IHaveMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(ToolItem.class)
public class ToolItemMixin {

  @Inject(method = "<init>", at = @At("TAIL"))
  public void registerTool(ToolMaterial material, Item.Settings settings, CallbackInfo ci){
    ((IGetEquipment<ToolItem, Class<? extends ToolItem>>)material).registerEquipment(((ToolItem)(Object)this), ((ToolItem)(Object)this).getClass());
  }
}

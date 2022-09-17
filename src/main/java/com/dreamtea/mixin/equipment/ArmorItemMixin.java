package com.dreamtea.mixin.equipment;

import com.dreamtea.imixin.IGetEquipment;
import com.dreamtea.imixin.IHaveMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorItem.class)
public class ArmorItemMixin {

  @Shadow @Final protected EquipmentSlot slot;

  @Inject(method = "<init>", at = @At("TAIL"))
  public void registerTool(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings, CallbackInfo ci){
    ((IGetEquipment<ArmorItem, EquipmentSlot>)material).registerEquipment(((ArmorItem)(Object)this), slot);
  }
}

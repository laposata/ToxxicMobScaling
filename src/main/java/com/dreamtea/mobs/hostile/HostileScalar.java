package com.dreamtea.mobs.hostile;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;

public class HostileScalar {

  protected final HostileConsts consts;

  public HostileScalar(HostileConsts consts) {
    this.consts = consts;
  }

  public float getDamageScale(){
    return consts.damageScalePercent;
  }
  public float getSpawnScale(){
    return consts.spawnChanceScale;
  }
  public float getXpScale(){
    return consts.xpScalePercent;
  }
  public float getHealthScale(){
    return consts.healthScalePercent;
  }

  public void updateEnchantments(Random random, LocalDifficulty localDifficulty, HostileEntity entity) {
    float f = localDifficulty.getClampedLocalDifficulty();
    this.enchantMainHandItem(random, f, entity);
    for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
      if (equipmentSlot.getType() != EquipmentSlot.Type.ARMOR) continue;
      this.enchantEquipment(random, f, equipmentSlot, entity);
    }
  }

  protected void enchantMainHandItem(Random random, float power, HostileEntity entity){
    enchant(random, power, entity, entity.getMainHandStack());
  }

  protected void enchantEquipment(Random random, float power, EquipmentSlot slot, HostileEntity entity) {
    enchant(random, power, entity, entity.getEquippedStack(slot));
  }

  private void enchant(Random random, float power, HostileEntity entity, ItemStack enchanting) {
    if (enchanting.isEmpty()){
      float levels = consts.maxEnchantValue * power;

      for(int i = 0; i < consts.enchantRolls && levels > consts.minEnchantValue; i ++) {
        if (random.nextFloat() < consts.oddForEnchant * power) {
          float enchantVal = consts.minEnchantValue + power * (float)random.nextInt((int)(levels - consts.minEnchantValue));
          if(consts.maxEnchantmentValueIsTotal){
            levels -= enchantVal;
          }
          entity.equipStack(
            EquipmentSlot.MAINHAND,
            EnchantmentHelper.enchant(random, entity.getMainHandStack(), (int)enchantVal, consts.allowTreasureEnchants)
          );
        }
      }
    }
  }

}

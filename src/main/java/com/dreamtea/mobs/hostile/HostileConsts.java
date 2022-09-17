package com.dreamtea.mobs.hostile;

public class HostileConsts {

  public final float healthScalePercent = 1;
  public final float damageScalePercent = 1;
  public final float spawnChanceScale = 1;
  public final float xpScalePercent = 1f;
  public final boolean allowTreasureEnchants = true;
  public final float minEnchantValue = 5;
  public final float maxEnchantValue = 25;
  /**
   * Designates if the max value is per enchantment or total value on the item.
   * Setting True means the weapons will be stronger
   * Consider (maxEnchantmentValueIsTotal: true, maxEnchantValue: 100, enchantRolls: 3):
   *    1 enchant level 70 is acceptable
   *    2 enchantments each level 45 is acceptable,
   *    3 enchantments each level 35 is not acceptable
   *
   * Consider (maxEnchantmentValueIsTotal: false, maxEnchantValue: 100, enchantRolls: 3):
   *    1 enchant level 70 is acceptable
   *    2 enchantments each level 70 is acceptable,
   *    3 enchantments each level 100 is acceptable
   */
  public final boolean maxEnchantmentValueIsTotal = false;
  public final int enchantRolls = 3;
  public final float oddForEnchant = .5f;

}

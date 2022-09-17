package com.dreamtea.mobs.zombie;

import com.dreamtea.mobs.hostile.HostileConsts;
import com.dreamtea.mobs.hostile.HostileScalar;
import com.dreamtea.util.RandomSelector;
import com.dreamtea.util.ScalingEquipment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PowderSnowBucketItem;
import net.minecraft.item.TridentItem;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;

import java.util.HashMap;
import java.util.Map;

public class ZombieScalar extends HostileScalar {
  private final Map<EquipmentSlot, Integer> EQUIPMENT_ODDS = Map.of(
    EquipmentSlot.CHEST, 20,
    EquipmentSlot.FEET, 50,
    EquipmentSlot.HEAD, 50,
    EquipmentSlot.LEGS, 30);

  private static Map<Item, Integer> DROWNED_WEAPONS = Map.of(
    Items.TRIDENT, 30,
    Items.FISHING_ROD, 20,
    Items.NAUTILUS_SHELL, 50
  );

  public ZombieScalar(ZombieConsts consts) {
    super(consts);
  }

  public void zombieEquipment(ZombieEntity zombie){
      zombie.equipStack(EquipmentSlot.MAINHAND, ScalingEquipment.getRandomTool(zombie.getRandom()).getDefaultStack());
      chooseEquipment(zombie, 2, 10);
  }

  public void drownedEquipment(ZombieEntity zombie){
    zombie.equipStack(EquipmentSlot.MAINHAND, new ItemStack(RandomSelector.getFromWeighted(DROWNED_WEAPONS, zombie.getRandom())));
    chooseEquipment(zombie, 3, 30);
  }

  public void chooseEquipment(ZombieEntity zombie, int repeatScale, int repeatPenalty){
    Map<EquipmentSlot, Integer> equip = new HashMap<>(EQUIPMENT_ODDS);
    EquipmentSlot updating = RandomSelector.getFromWeighted(equip, zombie.getRandom());
    int items = 0;
    while(updating != null && items < 8){
      ItemStack equippedStack = zombie.getEquippedStack(updating);
      if(equippedStack.isEmpty() || (!( equippedStack.getItem() instanceof ArmorItem))) {
        equippedStack = ScalingEquipment.getRandomArmor(zombie.getRandom(), updating).getDefaultStack();
      } else {
        equippedStack =
          ScalingEquipment.getArmor(
            ScalingEquipment.getNextArmorMaterial(((ArmorItem)equippedStack.getItem()).getMaterial()),
            updating
          ).getDefaultStack();
      }
      zombie.equipStack(updating, equippedStack);
      equip.put(updating, equip.get(updating) / repeatScale);
      items ++;
      updating = RandomSelector.getFromWeighted(equip, repeatPenalty * items, zombie.getRandom());

    }
  }
}

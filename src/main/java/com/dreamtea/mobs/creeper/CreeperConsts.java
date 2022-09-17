package com.dreamtea.mobs.creeper;

import com.dreamtea.mobs.hostile.HostileConsts;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.List;
import java.util.Map;

public class CreeperConsts extends HostileConsts {

  public static final String EFFECT_KEY = "creeperCloudEffects";
  public static final String CONSTANT_EFFECT_KEY = "creeperConstantEffects";

  public static final Map<StatusEffectInstance, Integer> weightedEffects =
    Map.of(new StatusEffectInstance(StatusEffects.INVISIBILITY, Integer.MAX_VALUE), 100,
      new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 1), 7,
      new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, 2), 3);

  public static final List<StatusEffectInstance> standardEffects =
    List.of(
      new StatusEffectInstance(StatusEffects.BLINDNESS, 20),
      new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 3),
      new StatusEffectInstance(StatusEffects.POISON, 10, 1),
      new StatusEffectInstance(StatusEffects.REGENERATION, 30, 3),
      new StatusEffectInstance(StatusEffects.WITHER, 5, 1),
      new StatusEffectInstance(StatusEffects.NAUSEA, 40, 0),
      new StatusEffectInstance(StatusEffects.WEAKNESS, 20, 2),
      new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 20, 2),
      new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 20, 4));

  public static final String CHARGED = "CHARGED";
  public static final String SPECIAL = "SPECIAL";
  public static final String STANDARD = "STANDARD";
  public static final String REROLL = "REROLL";
  public static final String NOTHING = "NOTHING";

  public static final Map<String, Integer> outcomes = Map.of(
    CHARGED, 25,
    SPECIAL, 75,
    STANDARD, 700,
    REROLL, 200
  );

}

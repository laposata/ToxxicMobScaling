package com.dreamtea.mobs.creeper;

import com.dreamtea.mobs.hostile.HostileScalar;
import com.dreamtea.util.RandomSelector;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dreamtea.mobs.creeper.CreeperConsts.CHARGED;
import static com.dreamtea.mobs.creeper.CreeperConsts.NOTHING;
import static com.dreamtea.mobs.creeper.CreeperConsts.REROLL;
import static com.dreamtea.mobs.creeper.CreeperConsts.SPECIAL;
import static com.dreamtea.mobs.creeper.CreeperConsts.STANDARD;
import static com.dreamtea.mobs.creeper.CreeperConsts.outcomes;
import static com.dreamtea.mobs.creeper.CreeperConsts.standardEffects;
import static com.dreamtea.mobs.creeper.CreeperConsts.weightedEffects;

public class CreeperScalar extends HostileScalar {

  private List<StatusEffectInstance> cloudEffect = new ArrayList<>();
  private List<StatusEffectInstance> constantEffects = new ArrayList<>();
  private boolean charging = false;
  public static final String CLOUD_KEY = "cloudEffects";
  public CreeperScalar() {
    super(new CreeperConsts());
  }

  public void chooseEffects(Random r){
    chooseEffects(r, outcomes, 0);
  }

  public void applyEffects(CreeperEntity creeper){
    constantEffects.forEach(eff -> {
      creeper.addStatusEffect(eff);
    });
  }

  private void chooseEffects(Random r, Map<String, Integer> outcomes, int nothing){
    String outcome = RandomSelector.getFromWeighted(outcomes, nothing, r);
    if(outcome == null) return;
    switch (outcome){
      case CHARGED:
        charging = true;
        break;
      case SPECIAL:
        StatusEffectInstance chosen = RandomSelector.getFromWeighted(weightedEffects, r);
        chosen.setPermanent(true);
        if(chosen.getEffectType().equals(StatusEffects.INVISIBILITY)){
          constantEffects.add(new StatusEffectInstance(StatusEffects.GLOWING, Integer.MAX_VALUE));
        }
        cloudEffect.add(chosen);
        constantEffects.add(chosen);
        break;
      case REROLL:
        chooseEffects(r, outcomes, nothing + 200);
        //not breaking as reroll also adds standard effect
      case STANDARD:
        cloudEffect.add(RandomSelector.getFromList(standardEffects, r));
        break;
    }
  }

  public List<StatusEffectInstance> cloudEffects(){
    return cloudEffect;
  }

  public void writeNbt(NbtCompound nbt){
    NbtList clouds = new NbtList();
    cloudEffect.forEach( eff ->{
        clouds.add(eff.writeNbt(new NbtCompound()));
    });
    nbt.put(CLOUD_KEY, clouds);
  }

  public void readFromNbt(NbtCompound nbt){
    if (nbt.contains(CLOUD_KEY, NbtElement.LIST_TYPE)) {
      NbtList nbtList = nbt.getList(CLOUD_KEY, NbtElement.COMPOUND_TYPE);
      for (int i = 0; i < nbtList.size(); ++i) {
        NbtCompound nbtCompound = nbtList.getCompound(i);
        StatusEffectInstance statusEffectInstance = StatusEffectInstance.fromNbt(nbtCompound);
        if (statusEffectInstance == null) continue;
        this.cloudEffect.add(statusEffectInstance);
      }
    }
    this.charging = nbt.getBoolean("ignited");
  }
  public boolean isCharging(){
    return this.charging;
  }

}

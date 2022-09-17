package com.dreamtea.mixin.mobs;

import net.minecraft.entity.mob.GhastEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(GhastEntity.ShootFireballGoal.class)
public class GhastEntityFireballMixin{
  public int shootCount = 0;
  @Redirect(method = "tick", at = @At(
    value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/entity/mob/GhastEntity$ShootFireballGoal;cooldown:I"
  ))
  public int activate3Times(GhastEntity.ShootFireballGoal instance){
    if(instance.cooldown == 20 || (instance.cooldown == -32 && shootCount < 5)){
      shootCount ++;
      return 20;
    }
    if(shootCount > 5){
      shootCount = 0;
    }
    return instance.cooldown;
  }

}

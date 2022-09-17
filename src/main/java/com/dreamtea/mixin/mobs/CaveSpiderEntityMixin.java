package com.dreamtea.mixin.mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CaveSpiderEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CaveSpiderEntity.class)
public class CaveSpiderEntityMixin {

  public StatusEffectInstance getVenom(int i){
    return new StatusEffectInstance(StatusEffects.WITHER, (i * 3) / 5, 1);
  }

  @Redirect(method = "tryAttack", at = @At(
    value = "INVOKE",
    target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z"
  ))
  public boolean witherInsteadOfPoison(LivingEntity instance, StatusEffectInstance effect, Entity source){
    instance.addStatusEffect(getVenom(effect.getDuration()), source);
    return false;
  }
}

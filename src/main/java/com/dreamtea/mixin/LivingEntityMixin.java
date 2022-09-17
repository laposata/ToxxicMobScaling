package com.dreamtea.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

  @Shadow protected abstract void damageShield(float amount);

  @Shadow @Final private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

  public LivingEntityMixin(EntityType<?> type, World world) {
    super(type, world);
  }

  @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V"))
  public void blazesDealExtraDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
    if(source.getAttacker() instanceof BlazeEntity){
      damageShield(amount * 2);
    }
  }

  @Redirect(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z",
    at = @At(value = "FIELD", opcode = Opcodes.GETFIELD,target = "Lnet/minecraft/entity/LivingEntity;activeStatusEffects:Ljava/util/Map;"))
  public Map<StatusEffect, StatusEffectInstance> createActiveEffectsMapIfNull(LivingEntity instance){
    if(activeStatusEffects == null){
      return new HashMap<>();
    }
    return activeStatusEffects;
  }
}

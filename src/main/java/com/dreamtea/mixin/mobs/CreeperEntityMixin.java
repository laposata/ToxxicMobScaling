package com.dreamtea.mixin.mobs;

import com.dreamtea.mobs.creeper.CreeperScalar;
import net.minecraft.entity.EntityType;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {

  @Shadow @Final private static TrackedData<Boolean> CHARGED;
  private CreeperScalar scalar;

  protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
    super(entityType, world);
  }

  @Inject(method = "<init>", at = @At("TAIL"))
  public void addEffectsAfterInit(EntityType entityType, World world, CallbackInfo ci){
    scalar.applyEffects((CreeperEntity)(Object)this);
    ((CreeperEntity)(Object)this).addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 2));
  }
  @Inject(method = "initDataTracker", at = @At("HEAD"))
  public void onSpawnSet(CallbackInfo ci) {
    scalar = new CreeperScalar();
  }

  @Inject(method = "initDataTracker", at = @At("TAIL"))
  public void onSpawnCharge(CallbackInfo ci) {
    scalar.chooseEffects(Random.create());
    this.dataTracker.set(CHARGED, scalar.isCharging());
  }

  @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
  public void readNbt(NbtCompound nbt, CallbackInfo ci){
    if(scalar != null){
      scalar.readFromNbt(nbt);
    }
  }

  @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
  public void saveNbt(NbtCompound nbt, CallbackInfo ci){
    if(scalar != null){
      scalar.writeNbt(nbt);
    }
  }

  @Redirect(method = "spawnEffectsCloud",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/CreeperEntity;getStatusEffects()Ljava/util/Collection;")
  )
  public Collection addOtherClouds(CreeperEntity instance){
    Collection<StatusEffectInstance> effects = instance.getStatusEffects();
    if(scalar != null){
      Collection<StatusEffectInstance> cloudEffects = scalar.cloudEffects();
      cloudEffects.addAll(effects);
      return cloudEffects;
    }
    return effects;
  }

}

package com.dreamtea.mixin.mobs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlazeEntity.class)
public abstract class BlazeEntityMixin extends HostileEntity {

  @Shadow protected abstract boolean isFireActive();

  protected BlazeEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
    super(entityType, world);
  }

  @Override
  public boolean damage(DamageSource source, float amount) {
    if(isFireActive() && source.getAttacker() != null){
      source.getAttacker().damage(DamageSource.mob(this), 1f + Random.create().nextInt(4));
    }
    return super.damage(source, amount);
  }
}

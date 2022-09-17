package com.dreamtea.mixin.mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpiderEntity.class)
public class SpiderEntityMixin extends HostileEntity {

  protected SpiderEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
    super(entityType, world);
  }

  public StatusEffectInstance getVenom(int i){
    return new StatusEffectInstance(StatusEffects.POISON, i * 20, 2);
  }
  @Override
  public boolean tryAttack(Entity target) {
    if (super.tryAttack(target)) {
      if (target instanceof LivingEntity) {
        int i = 0;
        if (this.world.getDifficulty() == Difficulty.NORMAL) {
          i = 7;
        } else if (this.world.getDifficulty() == Difficulty.HARD) {
          i = 15;
        }
        if (i > 0 && !(((Object)this) instanceof CaveSpiderEntity)) {
          ((LivingEntity)target).addStatusEffect(getVenom(i), this);
        }
      }
      return true;
    }
    return false;
  }

}

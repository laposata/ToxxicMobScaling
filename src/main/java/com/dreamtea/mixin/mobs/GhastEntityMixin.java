package com.dreamtea.mixin.mobs;

import com.dreamtea.imixin.IScale;
import com.dreamtea.mobs.ghast.GhastConsts;
import com.dreamtea.mobs.hostile.HostileScalar;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GhastEntity.class)
public class GhastEntityMixin implements IScale {
  private HostileScalar scalar;

  @Inject(method = "<init>", at = @At("RETURN"))
  public void scale(EntityType entityType, World world, CallbackInfo ci){
    scalar = new HostileScalar(new GhastConsts());

  }

  @Override
  public HostileScalar scale() {
    return scalar;
  }

  @Inject(method = "createGhastAttributes", at = @At("RETURN"), cancellable = true)
  private static void createGhastAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
    DefaultAttributeContainer returnValue = cir.getReturnValue().build();
    GhastConsts scales = new GhastConsts();
    cir.setReturnValue(MobEntity.createMobAttributes()
      .add(EntityAttributes.GENERIC_MAX_HEALTH, returnValue.getValue(EntityAttributes.GENERIC_MAX_HEALTH) * scales.healthScalePercent)
      .add(EntityAttributes.GENERIC_FOLLOW_RANGE,  returnValue.getValue(EntityAttributes.GENERIC_FOLLOW_RANGE))
    );
  }
}

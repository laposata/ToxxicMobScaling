package com.dreamtea.mixin.mobs;

import com.dreamtea.imixin.IScale;
import com.dreamtea.mobs.hostile.HostileScalar;
import com.dreamtea.mobs.zombie.ZombieScalar;
import com.dreamtea.mobs.zombie.ZombifiedPiglinConsts;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombifiedPiglinEntity.class)
public class ZombifiedPiglinEntityMixin extends HostileEntity implements IScale {

  protected HostileScalar scalar;
  protected ZombifiedPiglinEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
    super(entityType, world);
  }

  @Inject(method = "<init>", at = @At("RETURN"))
  public void initEnchants(EntityType entityType, World world, CallbackInfo ci){
    scalar = new ZombieScalar(new ZombifiedPiglinConsts());
  }

  @Override
  public HostileScalar scale() {
    return scalar;
  }
}

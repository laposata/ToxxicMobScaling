package com.dreamtea.mixin.mobs;

import com.dreamtea.imixin.IScale;
import com.dreamtea.mobs.hostile.HostileScalar;
import com.dreamtea.mobs.zombie.ZombieScalar;
import com.dreamtea.mobs.zombie.ZombifiedPiglinConsts;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin implements IScale {
  protected HostileScalar scalar;

  @Inject(method = "<init>*", at = @At("RETURN"))
  public void initEnchants(EntityType entityType, World world, CallbackInfo ci){
    scalar = new ZombieScalar(new ZombifiedPiglinConsts());
  }

  @Inject(method = "initEquipment", cancellable = true, at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/util/math/random/Random;nextFloat()F"))
  public void equipMyEquipmentInstead(Random random, LocalDifficulty localDifficulty, CallbackInfo ci){
    ((ZombieScalar)scalar).zombieEquipment((ZombieEntity)(Object)this);
    ci.cancel();
  }

  @Override
  public HostileScalar scale() {
    return scalar;
  }
}

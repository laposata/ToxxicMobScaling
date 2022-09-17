package com.dreamtea.mixin;

import com.dreamtea.imixin.IScale;
import com.dreamtea.mobs.hostile.HostileConsts;
import com.dreamtea.mobs.hostile.HostileScalar;
import com.dreamtea.util.RandomSelector;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HostileEntity.class)
public class HostileEntityMixin extends PathAwareEntity implements IScale {

  protected HostileScalar scalar;
  protected HostileEntityMixin(EntityType<? extends PathAwareEntity> entityType, World world) {
    super(entityType, world);
    this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = .2f;
  }

  @Inject(method = "<init>", at = @At("RETURN"))
  public void onInitAddScalarDefault(EntityType entityType, World world, CallbackInfo ci){
    this.scalar = new HostileScalar(new HostileConsts());
  }

  @Override
  public HostileScalar scale() {
    return scalar;
  }

  @Override
  public int getXpToDrop() {
    float xp = super.getXpToDrop() * scalar.getXpScale();
    return RandomSelector.roundRandomly(xp);
  }

  @Override
  protected void updateEnchantments(Random random, LocalDifficulty localDifficulty) {
    scalar.updateEnchantments(random, localDifficulty, ((HostileEntity)(Object)this));
  }

}

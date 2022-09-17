package com.dreamtea.mixin.mobs;

import com.dreamtea.imixin.IScale;
import com.dreamtea.mobs.hostile.HostileScalar;
import com.dreamtea.mobs.zombie.ZombieScalar;
import com.dreamtea.mobs.zombie.ZombifiedPiglinConsts;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DrownedEntity.class)
public class DrownedEntityMixin extends ZombieEntity implements IScale {
  protected HostileScalar scalar;

  public DrownedEntityMixin(EntityType<? extends ZombieEntity> entityType, World world) {
    super(entityType, world);
  }

  @Inject(method = "<init>*", at = @At("RETURN"))
  public void initEnchants(EntityType entityType, World world, CallbackInfo ci){
    scalar = new ZombieScalar(new ZombifiedPiglinConsts());
  }

  @Inject(method = "initEquipment", cancellable = true, at = @At("HEAD"))
  public void equipMyEquipmentInstead(Random random, LocalDifficulty localDifficulty, CallbackInfo ci){
    ((ZombieScalar)scalar).drownedEquipment((ZombieEntity)(Object)this);
    ci.cancel();
  }

  @Inject(method = "initialize", at = @At("RETURN"))
  public void shellIfPossible(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir){
    if(this.getEquippedStack(EquipmentSlot.OFFHAND).isEmpty()){
      this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.NAUTILUS_SHELL));
      this.updateDropChances(EquipmentSlot.OFFHAND);
    }
  }

  @Override
  public HostileScalar scale() {
    return scalar;
  }
}

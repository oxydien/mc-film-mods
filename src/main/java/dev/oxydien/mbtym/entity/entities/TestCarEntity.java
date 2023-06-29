package dev.oxydien.mbtym.entity.entities;

import dev.oxydien.mbtym.entity.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class TestCarEntity extends AnimalEntity implements GeoEntity {
	private  AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
	public TestCarEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (!this.hasPassengers()) {
			player.startRiding(this);

			return super.interactMob(player, hand);
		}

		return super.interactMob(player, hand);
	}

	@Override
	public void travel(Vec3d pos) {
		if (this.isAlive()) {
			if (this.hasPassengers()) {
				LivingEntity passenger = (LivingEntity) getPrimaryPassenger();
				this.prevYaw = getYaw();
				this.prevPitch = getPitch();

				assert passenger != null;
				setPitch(passenger.getPitch() * 0.5f);
				setRotation(getYaw(), getPitch());

				this.bodyYaw = this.getYaw();
				this.headYaw = this.bodyYaw;

				float z = passenger.forwardSpeed;

				if (z <= 0)
					z *= 0.25f;

				this.setMovementSpeed(0.3f);
				super.travel(new Vec3d(0, pos.y, z));

				// Rotate the vehicle if the passenger moves sideways
				if (passenger.sidewaysSpeed > 0 || passenger.sidewaysSpeed < 0) {
					if (passenger.forwardSpeed != 0) {
						float rotationAmount = (passenger.sidewaysSpeed > 0) ? -8 : 8;
						if (passenger.forwardSpeed < 0) {
							rotationAmount = -rotationAmount;
						}
						this.setYaw(this.getYaw() + rotationAmount);
						passenger.setYaw(passenger.getYaw() + rotationAmount);
						this.setRotation(this.getYaw(), this.getPitch());
					}
				}
			}
		}
	}


	@Nullable
	@Override
	public LivingEntity getPrimaryPassenger() {
		return getFirstPassenger() instanceof LivingEntity entity ? entity : null;
	}

	@Override
	public boolean isLogicalSideForUpdatingMovement() {
		return true;
	}

	// Adjust the rider's position while riding
	@Override
	public void updatePassengerPosition(Entity entity, PositionUpdater moveFunction) {
		super.updatePassengerPosition(entity, moveFunction);

		if (entity instanceof LivingEntity passenger) {
			moveFunction.accept(entity, getX(), getY() - 0.1f, getZ());

			this.prevPitch = passenger.prevPitch;
		}
	}

	public static DefaultAttributeContainer.Builder setAttributes() {
		return AnimalEntity.createAttributes()
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5f)
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 20D);

	}



	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new WanderAroundFarGoal(this,0.9f,1));
		this.goalSelector.add(2, new LookAroundGoal(this));
	}

	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return null;
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.5F;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
		controllerRegistrar.add(new AnimationController<>
			(this,"controller",0,this::predicate));

	}

	private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
		if (tAnimationState.isMoving()) {
			tAnimationState.getController().setAnimation(RawAnimation.begin()
				.then("ride", Animation.LoopType.LOOP));
		}
		else  {
			tAnimationState.getController().setAnimation(RawAnimation.begin()
				.then("idle", Animation.LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}
}

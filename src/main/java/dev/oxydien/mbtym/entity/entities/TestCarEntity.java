package dev.oxydien.mbtym.entity.entities;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class TestCarEntity extends AnimalEntity implements GeoEntity {
	private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
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

	private float speed = 0.0f; // Current speed
	private boolean lastGoForward = true;

	@Override
	public void travel(Vec3d pos) {
		if (this.isAlive()) {
			float acceleration = 0.008f;
			if (this.hasPassengers()) {
				LivingEntity passenger = getPrimaryPassenger();
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

				if (z != 0) {
					boolean goForward = z > 0;
					// Maximum speed
					float maxSpeed = 0.6f;
					if (goForward != lastGoForward) {
						speed = Math.max(0, speed - acceleration * 2);
					} else if (speed < maxSpeed) {
						speed += acceleration;
						if (speed > maxSpeed)
							speed = maxSpeed;
					}
					if (speed == 0) {
						lastGoForward = goForward;
					}
				} else {
					speed = Math.max(0, speed - acceleration);
				}

				this.setMovementSpeed(speed);
				super.travel(new Vec3d(0, pos.y, z + (lastGoForward ? speed : -speed)));

				if ((passenger.sidewaysSpeed > 0 || passenger.sidewaysSpeed < 0)&& speed != 0) {
						float rotationAmount = (passenger.sidewaysSpeed > 0) ? -8 : 8;
						if (passenger.forwardSpeed < 0) {
							rotationAmount = -rotationAmount;
						}

						float rotationSpeed = Math.abs(speed) * 0.9f;
						rotationAmount *= rotationSpeed;

						this.setYaw(this.getYaw() + rotationAmount);
						passenger.setYaw(passenger.getYaw() + rotationAmount);
						this.setHeadYaw(this.getYaw() + (passenger.sidewaysSpeed > 0 ? -15f : 15f));
						this.setRotation(this.getYaw(), this.getPitch());
				}
			} else {
				speed = Math.max(0, speed - acceleration * 2);
				this.setMovementSpeed(speed);
				super.travel(new Vec3d(0, pos.y, pos.z + (lastGoForward ? speed : -speed)));
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
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 1D);
	}


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

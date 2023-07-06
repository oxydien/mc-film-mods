package dev.oxydien.mbtym.entity.entities;

import dev.oxydien.mbtym.InitMod;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
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
			LivingEntity passenger = getPrimaryPassenger();
			assert passenger != null;
			if (passenger.getType() == EntityType.PLAYER) {
				InitMod.Log("Player ",passenger);
			}
			return super.interactMob(player, hand);
		}

		return super.interactMob(player, hand);
	}

	private float carSpeed = 0.0f;
	public static float DEFAULT_ACCELERATION = 0.014f;
	public static float DEFAULT_MAX_SPEED = 0.6f;
	private static float ACCELERATION = DEFAULT_ACCELERATION;
	private static float MAX_SPEED = DEFAULT_MAX_SPEED;
	private float carRotation = 0.0f;
	public static float ROTATION_AMOUNT = 0.3f;
	public static float MAX_ROTATION = 15.0f;

	public static void SetMaxSpeed(double speed) {
		MAX_SPEED = (float) speed;
	}

	public static double GetMaxSpeed() {
		return MAX_SPEED;
	}
	public static void SetAcceleration(double acceleration) {
		ACCELERATION = (float) acceleration;
	}

	public static double GetAcceleration() {
		return ACCELERATION;
	}

	@Override
	public void travel(Vec3d pos) {
		if (this.isAlive()) {
			this.prevYaw = getYaw();
			this.prevPitch = getPitch();
			if (this.hasPassengers()) {
				// NOT GO FASTER THAN MAX SPEED
				if (carSpeed > MAX_SPEED)
					carSpeed = MAX_SPEED;

				LivingEntity passenger = getPrimaryPassenger();
				assert passenger != null;
				float z = passenger.forwardSpeed;

				if (z != 0) {
					// Going forwards
					if (z > 0) {
						if (carSpeed < MAX_SPEED) {
							carSpeed += ACCELERATION;
							if (carSpeed > MAX_SPEED)
								carSpeed = MAX_SPEED;
						}
					}
					// Going backwards
					else {
						if (carSpeed > -MAX_SPEED) {
							carSpeed -= ACCELERATION;
							if (carSpeed < -MAX_SPEED)
								carSpeed = -MAX_SPEED;
						}
					}
				}
				// slowly slow down
				else if (carSpeed != 0){
					carSpeed += (carSpeed > 0) ? -(ACCELERATION / 2) : (ACCELERATION / 2);
					if (Math.abs(carSpeed) <= ACCELERATION / 2) {
						carSpeed = 0;
					}
				}

				// if trying to go sideways
				float rotationAmount = carRotation;
				if (passenger.sidewaysSpeed > 0 || passenger.sidewaysSpeed < 0) {
					// rotate to that direction
					carRotation += (passenger.sidewaysSpeed > 0) ? -ROTATION_AMOUNT : ROTATION_AMOUNT;
					if (carRotation > MAX_ROTATION)
						carRotation = MAX_ROTATION;
					if (carRotation < -MAX_ROTATION)
						carRotation = -MAX_ROTATION;
					// if going on reverse then reverse the rotation
					if (carSpeed < 0) {
						rotationAmount = -rotationAmount;
					}
				}
				// else take the rotation back to 0
				else if (carRotation != 0){
					if (carSpeed >= 0) {
						carRotation += (carRotation > 0) ? -(ROTATION_AMOUNT * 3) : (ROTATION_AMOUNT * 3);
						if (Math.abs(carRotation) <= ROTATION_AMOUNT * 3) {
							carRotation = 0;
						}
					}
					else {
						carRotation += (carRotation > 0) ? -(ROTATION_AMOUNT) : (ROTATION_AMOUNT);
						if (Math.abs(carRotation) <= ROTATION_AMOUNT) {
							carRotation = 0;
						}
					}
				}
				float rotationSpeed = Math.abs(carSpeed) * 0.9f;
				rotationAmount *= rotationSpeed;

				// model rotation and rotation
				if (carSpeed != 0) {
					this.setYaw(this.getYaw() + rotationAmount);
					passenger.setYaw(passenger.getYaw() + rotationAmount);
					if (carSpeed > 0) {
						this.setHeadYaw(this.getYaw() + carRotation * 3);
					}
					this.setRotation(this.getYaw(), this.getPitch());
				}
				else {
					this.setHeadYaw(this.getYaw() + carRotation);
					this.setRotation(this.getYaw(), this.getPitch());
				}

				// Debug chat message
				if (InitMod.DoDebug) {
					passenger.sendSystemMessage(
						Text.of(
							String.format(
								"Speed: %.1f / %.0f; Rotation: %.2f / %.2f; Yaw %.2f; %s",
								carSpeed * 10,
								MAX_SPEED * 10,
								carRotation,
								MAX_ROTATION,
								this.getYaw(),
								(Math.signum(carSpeed) == 1) ? "Forwards" : (Math.signum(carSpeed) == -1) ? "Backwards" : "NOWAY"
							)));
				}

				this.bodyYaw = this.getYaw();
				this.headYaw = this.getHeadYaw();
				// Same direction
				if ((carSpeed > 0 && passenger.forwardSpeed > 0) ||
					(carSpeed < 0 && passenger.forwardSpeed < 0)) {
					this.setMovementSpeed(Math.abs(carSpeed));
				// Opposite direction
				} else if ((carSpeed < 0 && passenger.forwardSpeed > 0) ||
					(carSpeed > 0 && passenger.forwardSpeed < 0)) {
					this.setMovementSpeed(-Math.abs(carSpeed));
				// zero
				} else {
					this.setMovementSpeed(Math.abs(carSpeed));
				}
				super.travel(new Vec3d(0, pos.y, z + carSpeed));
			} else {
				carSpeed += (carSpeed > 0) ? -(ACCELERATION) : (ACCELERATION);
				if (Math.abs(carSpeed) <= ACCELERATION) {
					carSpeed = 0;
				}
				this.setMovementSpeed(Math.abs(carSpeed));
				this.setRotation(this.getYaw(), this.getPitch());
				this.bodyYaw = this.getYaw();
				this.headYaw = this.bodyYaw;
				super.travel(new Vec3d(0, pos.y, pos.z + carSpeed));
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

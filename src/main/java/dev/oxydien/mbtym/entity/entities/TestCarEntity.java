package dev.oxydien.mbtym.entity.entities;

import dev.oxydien.mbtym.InitMod;
import io.netty.handler.codec.DatagramPacketDecoder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
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

	private static TrackedData<Float> carSpeed = DataTracker.registerData(TestCarEntity.class, TrackedDataHandlerRegistry.FLOAT);

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
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(carSpeed,0.0f);
	}

	@Override
	public void travel(Vec3d pos) {
		if (this.isAlive()) {
			this.prevYaw = getYaw();
			this.prevPitch = getPitch();
			float gotCarSpeed = this.dataTracker.get(carSpeed);
			if (this.hasPassengers()) {
				LivingEntity passenger = getPrimaryPassenger();
				assert passenger != null;
				float forwardSpeed = passenger.forwardSpeed;

				// if player tries to move
				if (forwardSpeed != 0) {
					// Going forwards
					if (forwardSpeed > 0) {
						if (gotCarSpeed < MAX_SPEED) {
							gotCarSpeed += ACCELERATION;
							if (gotCarSpeed > MAX_SPEED)
								gotCarSpeed = MAX_SPEED;
						}
						else {
							gotCarSpeed = MAX_SPEED;
						}
						// slow down faster
						if (gotCarSpeed < 0) {
							gotCarSpeed += ACCELERATION;
						}
					}
					// Going backwards
					else {
						if (gotCarSpeed > -MAX_SPEED) {
							gotCarSpeed -= ACCELERATION;
							if (gotCarSpeed < -MAX_SPEED)
								gotCarSpeed = -MAX_SPEED;
						}
						else {
							gotCarSpeed = -MAX_SPEED;
						}
						// slow down faster
						if (gotCarSpeed > 0) {
							gotCarSpeed -= ACCELERATION;
						}
					}
				}
				// slowly slow down
				else if (gotCarSpeed != 0){
					gotCarSpeed += (gotCarSpeed > 0) ? -(ACCELERATION / 2) : (ACCELERATION / 2);
					if (Math.abs(gotCarSpeed) <= ACCELERATION / 2) {
						gotCarSpeed = 0;
					}
				}

				// if player is trying to go sideways
				float rotationSpeed = Math.abs(gotCarSpeed) * 0.9f;
				float rotationAmount = carRotation * rotationSpeed;
				if (passenger.sidewaysSpeed > 0 || passenger.sidewaysSpeed < 0) {
					// rotate to that direction
					carRotation += (passenger.sidewaysSpeed > 0) ? -ROTATION_AMOUNT : ROTATION_AMOUNT;
					if (carRotation > MAX_ROTATION)
						carRotation = MAX_ROTATION;
					if (carRotation < -MAX_ROTATION)
						carRotation = -MAX_ROTATION;
					// if going on reverse then reverse the rotation
					if (gotCarSpeed < 0) {
						rotationAmount = -rotationAmount;
					}
				}
				// else take the rotation back to 0
				else if (carRotation != 0){
					if (gotCarSpeed >= 0) {
						carRotation += (carRotation > 0) ? -(ROTATION_AMOUNT * 3) : (ROTATION_AMOUNT * 3);
						if (Math.abs(carRotation) <= ROTATION_AMOUNT * 3) {
							carRotation = 0;
						}
					}
					else {
						// carRotation += (carRotation > 0) ? -(ROTATION_AMOUNT) : (ROTATION_AMOUNT);
						// if (Math.abs(carRotation) <= ROTATION_AMOUNT) {
							carRotation = 0;
						// }
					}
				}

				// model rotation and rotation
				if (gotCarSpeed != 0) {
					if (this.getWorld().isClient()) {
						this.setYaw(this.getYaw() + rotationAmount);
						this.bodyYaw = this.getYaw() + rotationAmount;
						passenger.setYaw(passenger.getYaw() + rotationAmount);
						if (gotCarSpeed > 0) {
							this.setHeadYaw(this.getYaw() + carRotation * 3);
							this.headYaw = this.getHeadYaw() + carRotation * 3;
						}
						this.setRotation(this.getYaw(), this.getPitch());
					}
					this.serverYaw = this.getYaw() + rotationAmount;
				}
				else {
					this.serverYaw = this.getYaw();
					this.setHeadYaw(this.getYaw() + carRotation);
					this.setRotation(this.getYaw(), this.getPitch());
				}

				// Debug chat message
				if (InitMod.DoDebug && !this.getWorld().isClient()) {
					passenger.sendSystemMessage(
						Text.of(
							String.format(
								"Sp %.1f / %.1f;Rot %.2f / %.2f;Yaw %.2f; %s;Pos %.1f %.1f %.1f",
								gotCarSpeed * 10,
								MAX_SPEED * 10,
								carRotation,
								MAX_ROTATION,
								this.getYaw(),
								(Math.signum(gotCarSpeed) == 1) ? "+" : (Math.signum(gotCarSpeed) == -1) ? "-" : "0",
								this.getPos().x,
								this.getPos().y,
								this.getPos().z
							)));
				}

				// Same direction
				if ((gotCarSpeed > 0 && forwardSpeed > 0) ||
					(gotCarSpeed < 0 && forwardSpeed < 0)) {
					this.setMovementSpeed(Math.abs(gotCarSpeed));
				// Opposite direction
				} else if ((gotCarSpeed < 0 && forwardSpeed > 0) ||
					(gotCarSpeed > 0 && forwardSpeed < 0)) {
					this.setMovementSpeed(-Math.abs(gotCarSpeed));
				// zero
				} else {
					this.setMovementSpeed(Math.abs(gotCarSpeed));
				}
				super.travel(new Vec3d(0, pos.y, forwardSpeed + gotCarSpeed));
				this.dataTracker.set(carSpeed,gotCarSpeed);
			} else {
				gotCarSpeed += (gotCarSpeed > 0) ? -(ACCELERATION) : (ACCELERATION);
				if (Math.abs(gotCarSpeed) <= ACCELERATION) {
					gotCarSpeed = 0;
				}
				this.setMovementSpeed(Math.abs(gotCarSpeed));
				super.travel(new Vec3d(0, pos.y, pos.z + gotCarSpeed));
				this.dataTracker.set(carSpeed,gotCarSpeed);
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
		}
	}

	public static DefaultAttributeContainer.Builder setAttributes() {
		return AnimalEntity.createAttributes()
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

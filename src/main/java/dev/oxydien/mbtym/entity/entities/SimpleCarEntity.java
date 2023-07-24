package dev.oxydien.mbtym.entity.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SimpleCarEntity extends AnimalEntity implements GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public SimpleCarEntity(EntityType<? extends AnimalEntity> entityType, World level) {
		super(entityType, level);
	}

	// Let the player ride the entity
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (!this.hasPassengers()) {
			player.startRiding(this);

			return super.interactMob(player, hand);
		}

		return super.interactMob(player, hand);
	}

	// Turn off step sounds since it's a bike
	@Override
	protected void playStepSound(BlockPos pos, BlockState block) {}

	// Apply player-controlled movement
	@Override
	public void travel(Vec3d pos) {
		if (this.isAlive()) {
			if (this.hasPassengers()) {
				LivingEntity passenger = getPrimaryPassenger();
				this.prevYaw = getYaw();
				this.prevPitch = getPitch();

				setYaw(passenger.getYaw());
				setPitch(passenger.getPitch() * 0.5f);
				setRotation(getYaw(), getPitch());

				this.bodyYaw = this.getYaw();
				this.headYaw = this.bodyYaw;
				float x = passenger.sidewaysSpeed * 0.5F;
				float z = passenger.forwardSpeed;

				if (z <= 0)
					z *= 0.25f;

				this.setMovementSpeed(0.3f);
				super.travel(new Vec3d(x, pos.y, z));
			}
			else {
				super.travel(new Vec3d(pos.x / 10, pos.y / 10, pos.z / 10));
			}
		}
	}

	// Get the controlling passenger
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
			moveFunction.accept(entity, getX(), getY() - 0.2f, getZ());

			this.prevPitch = passenger.prevPitch;
		}
	}

	// Add our idle/moving animation controller
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
		controllerRegistrar.add(new AnimationController<>
			(this,"controller",3,this::predicate));

	}

	private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
		if (tAnimationState.isMoving()) {
			tAnimationState.getController().setAnimation(RawAnimation.begin()
				.then("riding", Animation.LoopType.LOOP));
		}
		else  {
			tAnimationState.getController().setAnimation(RawAnimation.begin()
				.then("starting", Animation.LoopType.LOOP));
		}
		return PlayState.CONTINUE;
	}
	public static DefaultAttributeContainer.Builder setAttributes() {
		return AnimalEntity.createAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 1D);
	}
	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return null;
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.5F;
	}
}

package dev.oxydien.mbtym.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

public class CoffeeMachineEntity extends BlockEntity implements GeoBlockEntity {
	private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);


	public CoffeeMachineEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.COFFEE_MACHINE_ENTITY, pos, state);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar){
		controllerRegistrar.add(new AnimationController<>(this, "controller", this::predicate));
	}

	private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
		tAnimationState.getController().setAnimation(RawAnimation.begin().then("fill", Animation.LoopType.LOOP));
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	public double getTick(Object blockEntity) {
		return RenderUtils.getCurrentTick();
	}
}

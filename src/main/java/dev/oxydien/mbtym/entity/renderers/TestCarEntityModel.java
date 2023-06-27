package dev.oxydien.mbtym.entity.renderers;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.entity.entities.TestCarEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TestCarEntityModel extends GeoModel<TestCarEntity> {
	@Override
	public Identifier getModelResource(TestCarEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "geo/test_car.geo.json");
	}

	@Override
	public Identifier getTextureResource(TestCarEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "textures/entity/test_car.png");
	}

	@Override
	public Identifier getAnimationResource(TestCarEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "animations/test_car.geo.json");
	}

	@Override
	public void setCustomAnimations(TestCarEntity animatable, long instanceId, AnimationState<TestCarEntity> animationState) {
	}
}

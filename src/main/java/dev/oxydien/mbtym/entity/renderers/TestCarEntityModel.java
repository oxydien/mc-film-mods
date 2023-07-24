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
		return new Identifier(InitMod.MOD_ID, "geo/simple_car.geo.json");
	}

	@Override
	public Identifier getTextureResource(TestCarEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "textures/entity/red_car.png");
	}

	@Override
	public Identifier getAnimationResource(TestCarEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "animations/car_entity.animation.json");
	}

	@Override
	public void setCustomAnimations(TestCarEntity animatable, long instanceId, AnimationState<TestCarEntity> animationState) {
		CoreGeoBone wheel_f_l = getAnimationProcessor().getBone("left_front_tire");
		CoreGeoBone wheel_f_r = getAnimationProcessor().getBone("right_front_tire");
		CoreGeoBone steering_wheel = getAnimationProcessor().getBone("stearing_wheal");

		if (wheel_f_l != null && wheel_f_r != null) {
			EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			wheel_f_l.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
			wheel_f_r.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
			steering_wheel.setRotZ(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}
}

package dev.oxydien.mbtym.entity.renderers.SimpleCars;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.entity.entities.SimpleCarEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class WhiteCarModel extends GeoModel<SimpleCarEntity> {
	@Override
	public Identifier getModelResource(SimpleCarEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "geo/simple_car.geo.json");
	}

	@Override
	public Identifier getTextureResource(SimpleCarEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "textures/entity/white_car.png");
	}

	@Override
	public Identifier getAnimationResource(SimpleCarEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "animations/car_entity.animation.json");
	}
}

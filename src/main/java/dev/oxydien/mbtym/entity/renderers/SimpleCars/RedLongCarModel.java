package dev.oxydien.mbtym.entity.renderers.SimpleCars;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.entity.entities.SimpleCarEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RedLongCarModel extends GeoModel<SimpleCarEntity> {
	@Override
	public Identifier getModelResource(SimpleCarEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "geo/long_car.geo.json");
	}

	@Override
	public Identifier getTextureResource(SimpleCarEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "textures/entity/red_long_car.png");
	}

	@Override
	public Identifier getAnimationResource(SimpleCarEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "animations/car_entity.animation.json");
	}
}

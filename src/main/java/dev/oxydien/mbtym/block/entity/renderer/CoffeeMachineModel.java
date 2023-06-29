package dev.oxydien.mbtym.block.entity.renderer;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.block.entity.CoffeeMachineEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CoffeeMachineModel extends GeoModel<CoffeeMachineEntity> {
	@Override
	public Identifier getModelResource(CoffeeMachineEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "geo/coffee_machine.geo.json");
	}

	@Override
	public Identifier getTextureResource(CoffeeMachineEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "textures/block/coffee_machine.png");
	}

	@Override
	public Identifier getAnimationResource(CoffeeMachineEntity animatable) {
		return new Identifier(InitMod.MOD_ID, "animations/coffee_block.animation.json");
	}
}

package dev.oxydien.mbtym.item.client;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.item.CoffeeMachineItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CoffeeMachineItemModel extends GeoModel<CoffeeMachineItem> {
	@Override
	public Identifier getModelResource(CoffeeMachineItem animatable) {
		return new Identifier(InitMod.MOD_ID, "geo/coffee_machine.geo.json");
	}

	@Override
	public Identifier getTextureResource(CoffeeMachineItem animatable) {
		return new Identifier(InitMod.MOD_ID, "textures/block/coffee_machine.png");
	}

	@Override
	public Identifier getAnimationResource(CoffeeMachineItem animatable) {
		return new Identifier(InitMod.MOD_ID, "animations/coffee_block.animation.json");
	}

}

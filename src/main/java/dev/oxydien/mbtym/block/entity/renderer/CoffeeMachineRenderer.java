package dev.oxydien.mbtym.block.entity.renderer;

import dev.oxydien.mbtym.block.entity.CoffeeMachineEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class CoffeeMachineRenderer extends GeoBlockRenderer<CoffeeMachineEntity> {
	public CoffeeMachineRenderer(BlockEntityRendererFactory.Context context) {
		super(new CoffeeMachineModel());
	}
}

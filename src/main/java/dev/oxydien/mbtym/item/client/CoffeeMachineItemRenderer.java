package dev.oxydien.mbtym.item.client;

import dev.oxydien.mbtym.item.CoffeeMachineItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class CoffeeMachineItemRenderer extends GeoItemRenderer<CoffeeMachineItem> {
	public CoffeeMachineItemRenderer() {
		super(new CoffeeMachineItemModel());
	}
}

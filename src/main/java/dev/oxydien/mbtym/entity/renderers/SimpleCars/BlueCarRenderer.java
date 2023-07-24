package dev.oxydien.mbtym.entity.renderers.SimpleCars;

import dev.oxydien.mbtym.entity.entities.SimpleCarEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlueCarRenderer extends GeoEntityRenderer<SimpleCarEntity> {
	public BlueCarRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new BlueCarModel());
		this.withScale(1.6f,1.6f);
	}
}

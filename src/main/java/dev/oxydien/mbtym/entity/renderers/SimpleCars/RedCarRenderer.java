package dev.oxydien.mbtym.entity.renderers.SimpleCars;

import dev.oxydien.mbtym.entity.entities.SimpleCarEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RedCarRenderer extends GeoEntityRenderer<SimpleCarEntity> {
	public RedCarRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new RedCarModel());
		this.withScale(1.5f,1.5f);
	}
}


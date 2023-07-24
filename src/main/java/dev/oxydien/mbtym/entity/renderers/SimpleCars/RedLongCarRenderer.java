package dev.oxydien.mbtym.entity.renderers.SimpleCars;

import dev.oxydien.mbtym.entity.entities.SimpleCarEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RedLongCarRenderer extends GeoEntityRenderer<SimpleCarEntity> {
	public RedLongCarRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new RedLongCarModel());
		this.withScale(1.5f,1.5f);
	}
}

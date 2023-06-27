package dev.oxydien.mbtym.entity.renderers;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.entity.entities.TestCarEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TestCarEntityRenderer extends GeoEntityRenderer<TestCarEntity> {
	public TestCarEntityRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new TestCarEntityModel());
	}
}

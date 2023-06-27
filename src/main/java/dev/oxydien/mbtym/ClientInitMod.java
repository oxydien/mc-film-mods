package dev.oxydien.mbtym;

import dev.oxydien.mbtym.entity.ModEntities;
import dev.oxydien.mbtym.entity.entities.TestCarEntity;
import dev.oxydien.mbtym.entity.renderers.TestCarEntityRenderer;
import dev.oxydien.mbtym.fluid.ModFluid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

@Environment(EnvType.CLIENT)
public class ClientInitMod implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {

		FluidRenderHandlerRegistry.INSTANCE.register(ModFluid.STILL_HEAVY_AIR, ModFluid.FLOWING_HEAVY_AIR,
			new SimpleFluidRenderHandler(
				new Identifier("mbtym:block/heavy_air_block_still"),
				new Identifier("mbtym:block/heavy_air_block_flow"),
				0xA1E038D0
			));

		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
			ModFluid.STILL_HEAVY_AIR, ModFluid.FLOWING_HEAVY_AIR);


		EntityRendererRegistry.register(ModEntities.TEST_CAR, TestCarEntityRenderer::new);
	}
}

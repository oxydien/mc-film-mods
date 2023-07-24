package dev.oxydien.mbtym;

import dev.oxydien.mbtym.block.entity.ModBlockEntities;
import dev.oxydien.mbtym.block.entity.renderer.CoffeeMachineRenderer;
import dev.oxydien.mbtym.entity.ModEntities;
import dev.oxydien.mbtym.entity.renderers.SimpleCars.*;
import dev.oxydien.mbtym.entity.renderers.TestCarEntityRenderer;
import dev.oxydien.mbtym.fluid.ModFluid;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

public class ClientInitMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FluidRenderHandlerRegistry.INSTANCE.register(ModFluid.STILL_HEAVY_AIR, ModFluid.FLOWING_HEAVY_AIR,
			new SimpleFluidRenderHandler(
				new Identifier("mbtym:block/heavy_air_block_still"),
				new Identifier("mbtym:block/heavy_air_block_flow"),
				0xA1E038D0
			));

		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
			ModFluid.STILL_HEAVY_AIR, ModFluid.FLOWING_HEAVY_AIR);


		EntityRendererRegistry.register(ModEntities.TEST_CAR, TestCarEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.WHITE_CAR, WhiteCarRenderer::new);
		EntityRendererRegistry.register(ModEntities.BLUE_CAR, BlueCarRenderer::new);
		EntityRendererRegistry.register(ModEntities.BLUE_LONG_CAR, BlueLongCarRenderer::new);
		EntityRendererRegistry.register(ModEntities.RED_CAR, RedCarRenderer::new);
		EntityRendererRegistry.register(ModEntities.RED_LONG_CAR, RedLongCarRenderer::new);
		EntityRendererRegistry.register(ModEntities.ORANGE_LONG_CAR, OrangeLongCarRenderer::new);

		BlockEntityRendererFactories.register(ModBlockEntities.COFFEE_MACHINE_ENTITY, CoffeeMachineRenderer::new);
	}
}

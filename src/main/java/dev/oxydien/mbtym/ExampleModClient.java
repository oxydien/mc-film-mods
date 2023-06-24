// package com.example.example_mod;
//
// import entity.dev.oxydien.mbtym.ModEntities;
// import com.example.example_mod.entity.renderers.SlideEntityRenderer;
// import net.fabricmc.api.EnvType;
// import net.fabricmc.api.Environment;
// import net.minecraft.client.render.entity.model.EntityModelLayer;
// import net.minecraft.util.Identifier;
// import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
//
// @Environment(EnvType.CLIENT)
// public class ExampleModClient implements ClientModInitializer {
// 	public static final EntityModelLayer MODEL_CUBE_LAYER = new EntityModelLayer(new Identifier("entitytesting", "cube"), "main");
// 	@Override
// 	public void onInitializeClient() {
//
// 		EntityRendererRegistry.INSTANCE.register(ModEntities.SLIDER, (context) -> {
// 			return new SlideEntityRenderer(context);
// 		});
// 		// In 1.17, use EntityRendererRegistry.register (seen below) instead of EntityRendererRegistry.INSTANCE.register (seen above)
// 		EntityRendererRegistry.register(ModEntities.SLIDER, (context) -> {
// 			return new SlideEntityRenderer(context);
// 		});
//
// 		EntityModelLayerRegistry.registerModelLayer(MODEL_CUBE_LAYER, CubeEntityModel::getTexturedModelData);
// 	}
// }
//

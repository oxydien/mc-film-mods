package dev.oxydien.mbtym.data;

import dev.oxydien.mbtym.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.ModelIds;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerParentedItemModel(ModItems.TEST_CAR_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {

	}
}

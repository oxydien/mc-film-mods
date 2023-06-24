package dev.oxydien.mbtym;

import dev.oxydien.mbtym.block.ModBlocks;
import dev.oxydien.mbtym.entity.ModEntities;
import dev.oxydien.mbtym.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitMod implements ModInitializer {
	public static final String MOD_ID = "mbtym";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Create a custom item group
	public static final ItemGroup CUSTOM_GROUP = FabricItemGroup.builder()
		.name(Text.translatable("itemGroup.mbtym.mbtym"))
		.icon(() -> new ItemStack(ModBlocks.EARTH_BLOCK.asItem())) // Set the icon for the item group
		.build();

	@Override
	public void onInitialize(ModContainer mod) {

		Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "mbtym"), CUSTOM_GROUP);

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();
		LOGGER.info("Loaded!");
	}
}

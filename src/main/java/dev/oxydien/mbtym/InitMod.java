package dev.oxydien.mbtym;

import dev.oxydien.mbtym.block.ModBlocks;
import dev.oxydien.mbtym.block.entity.ModBlockEntities;
import dev.oxydien.mbtym.entity.ModEntities;
import dev.oxydien.mbtym.entity.entities.TestCarEntity;
import dev.oxydien.mbtym.fluid.ModFluid;
import dev.oxydien.mbtym.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class InitMod implements ModInitializer {
	public static final String MOD_ID = "mbtym";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Create a custom item group
	public static final ItemGroup CUSTOM_GROUP = FabricItemGroup.builder()
		.name(Text.translatable("itemGroup.mbtym.mbtym"))
		.icon(() -> new ItemStack(ModBlocks.EARTH_BLOCK.asItem())) // Set the icon for the item group
		.build();

	public static void Log(String message, Object... var2) {
		LOGGER.info("<MBTYM>: " + message, var2);
	}

	@Override
	public void onInitialize(ModContainer mod) {

		Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "mbtym"), CUSTOM_GROUP);

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();
		FabricDefaultAttributeRegistry.register(ModEntities.TEST_CAR, TestCarEntity.setAttributes());
		ModBlockEntities.registerBlockEntities();
		ModFluid.register();
		Log("Loaded!");
	}
}

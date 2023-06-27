package dev.oxydien.mbtym.fluid;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class ModFluid {

	public static FlowableFluid STILL_HEAVY_AIR;
	public static FlowableFluid FLOWING_HEAVY_AIR;
	public static Block HEAVY_AIR_BLOCK;
	public static Item HEAVY_AIR_BUCKET;

	public static void register() {
		STILL_HEAVY_AIR = Registry.register(Registries.FLUID,
			new Identifier(InitMod.MOD_ID, "heavy_air"), new HeavyAir.Still());
		FLOWING_HEAVY_AIR = Registry.register(Registries.FLUID,
			new Identifier(InitMod.MOD_ID, "flowing_heavy_air"), new HeavyAir.Flowing());

		HEAVY_AIR_BLOCK = Registry.register(Registries.BLOCK, new Identifier(InitMod.MOD_ID, "heavy_air_block"),
			new FluidBlock(ModFluid.STILL_HEAVY_AIR, QuiltBlockSettings.copyOf(Blocks.WATER)){ });
		HEAVY_AIR_BUCKET = Registry.register(Registries.ITEM, new Identifier(InitMod.MOD_ID, "heavy_air_bucket"),
			new BucketItem(ModFluid.STILL_HEAVY_AIR, new QuiltItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));

		ModItems.AddItemToGroup(HEAVY_AIR_BUCKET);

		InitMod.Log("Fluids loaded...");
	}
}

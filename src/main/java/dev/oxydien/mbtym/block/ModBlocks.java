package dev.oxydien.mbtym.block;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.block.blocks.BookBlock;
import dev.oxydien.mbtym.block.blocks.EarthBlock;
import dev.oxydien.mbtym.block.blocks.PapersBlock;
import dev.oxydien.mbtym.block.blocks.CoffeeMachineBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;


public class ModBlocks {
	public static Block EARTH_BLOCK = registerblock("earth_block", EarthBlock.EARTH_BLOCK);
	public static Block BOOK_BLOCK = registerblock("books_block", BookBlock.BOOK_BLOCK);
	public static Block PAPERS_BLOCK = registerblock("papers_block", PapersBlock.PAPERS_BLOCK);
	public static Block COFFEE_MACHINE_BLOCK = Registry.register(Registries.BLOCK,
		new Identifier(InitMod.MOD_ID, "coffee_machine_block"),
		new CoffeeMachineBlock(QuiltBlockSettings.copyOf(Blocks.STONE).nonOpaque()));


	private  static Block registerblock(String name, Block block) {
		registerBlockItem(name,block);
		return Registry.register(Registries.BLOCK, new Identifier(InitMod.MOD_ID, name), block);
	}

	private  static void registerBlockItem(String name, Block block) {
		final Item NewItem = Registry.register(Registries.ITEM, new Identifier(InitMod.MOD_ID, name),
			new BlockItem(block, new QuiltItemSettings()));
		ItemGroupEvents.modifyEntriesEvent(RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(InitMod.MOD_ID,"mbtym")))
			.register(content -> {
				content.addItem(NewItem);
			});
	}

	public  static  void  registerModBlocks() {
		InitMod.Log("Registering blocks!");
	}
}

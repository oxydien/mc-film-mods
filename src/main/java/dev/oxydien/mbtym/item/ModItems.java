package dev.oxydien.mbtym.item;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.block.ModBlocks;
import dev.oxydien.mbtym.entity.ModEntities;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.awt.*;

public class ModItems {

	public  static final Item FINGER = registerItem("finger", new FingerItem(new QuiltItemSettings()));
	public  static final Item SPIN = registerItem("spin", new SpinItem(new QuiltItemSettings().maxCount(128)));
	public  static final Item COFFEE_MACHINE_ITEM = registerItem("coffee_machine_block", new CoffeeMachineItem(ModBlocks.COFFEE_MACHINE_BLOCK, new QuiltItemSettings()));
	public static final Item TABLET = registerItem("tablet", new Item(new QuiltItemSettings().rarity(Rarity.RARE)));
	public static final Item TELEPHONE = registerItem("telephone", new Item(new QuiltItemSettings().rarity(Rarity.RARE)));
	public static final Item TELEPHONE_USED = registerItem("telephone_used", new TelephoneItem(new QuiltItemSettings().rarity(Rarity.EPIC)));
	public static final Item CUP_OF_COFFEE = registerItem("cup_of_coffee", new HoneyBottleItem(new QuiltItemSettings().food(FoodComponents.HONEY_BOTTLE).maxCount(16)));
	public static final Item DEBUG_ITEM = registerItem("debug_item", new DebugItem(new QuiltItemSettings()));
	public static  final Item KNIFE_ITEM = registerItem("knife_item", new Item(new QuiltItemSettings()));
	public  static final Item TEST_CAR_SPAWN_EGG = registerItem("test_car_spawn_egg", new SpawnEggItem(ModEntities.TEST_CAR, 0x800200, 0x09f7df, new QuiltItemSettings()));
	public  static final Item WHITE_CAR_SPAWN_EGG = registerItem("white_car_spawn_egg", new SpawnEggItem(ModEntities.WHITE_CAR, 0xffffff, 0x09f7df, new QuiltItemSettings()));
	public  static final Item BLUE_CAR_SPAWN_EGG = registerItem("blue_car_spawn_egg", new SpawnEggItem(ModEntities.BLUE_CAR, 0x0000ff, 0x09f7df, new QuiltItemSettings()));
	public  static final Item BLUE_LONG_CAR_SPAWN_EGG = registerItem("blue_long_car_spawn_egg", new SpawnEggItem(ModEntities.BLUE_LONG_CAR, 0x0022ff, 0x09f7df, new QuiltItemSettings()));
	public  static final Item RED_CAR_SPAWN_EGG = registerItem("red_car_spawn_egg", new SpawnEggItem(ModEntities.RED_CAR, 0xff0000, 0x09f7df, new QuiltItemSettings()));
	public  static final Item RED_LONG_CAR_SPAWN_EGG = registerItem("red_long_car_spawn_egg", new SpawnEggItem(ModEntities.RED_LONG_CAR, 0xff2200, 0x09f7df, new QuiltItemSettings()));
	public  static final Item ORANGE_LONG_CAR_SPAWN_EGG = registerItem("orange_long_car_spawn_egg", new SpawnEggItem(ModEntities.ORANGE_LONG_CAR, 0xffee00, 0x09f7df, new QuiltItemSettings()));
	private static Item registerItem(String name, Item item)	 {
		final Item NewItem = Registry.register(Registries.ITEM, new Identifier(InitMod.MOD_ID, name), item);
		ItemGroupEvents.modifyEntriesEvent(RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(InitMod.MOD_ID,"mbtym")))
			.register(content -> {
			content.addItem(NewItem);
		});
		return NewItem;
	}

	public static void AddItemToGroup(Item item) {
		ItemGroupEvents.modifyEntriesEvent(RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(InitMod.MOD_ID,"mbtym")))
			.register(content -> {
				content.addItem(item);
			});
	}
	public static void registerModItems() {
		AddItemToGroup(COFFEE_MACHINE_ITEM);
		InitMod.Log("Registering items!");
	}
}

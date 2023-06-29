package dev.oxydien.mbtym.item;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.block.ModBlocks;
import dev.oxydien.mbtym.entity.ModEntities;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class ModItems {

	public  static final Item FINGER = registerItem("finger", new FingerItem(new QuiltItemSettings()));
	public  static final Item SPIN = registerItem("spin", new SpinItem(new QuiltItemSettings().maxCount(128)));
	public  static final Item TEST_CAR_SPAWN_EGG = registerItem("test_car_spawn_egg", new SpawnEggItem(ModEntities.TEST_CAR, 0x56ea5f, 0xffffff, new QuiltItemSettings()));
	public  static final Item COFFEE_MACHINE_ITEM = registerItem("coffee_machine_block", new CoffeeMachineItem(ModBlocks.COFFEE_MACHINE_BLOCK, new QuiltItemSettings()));

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

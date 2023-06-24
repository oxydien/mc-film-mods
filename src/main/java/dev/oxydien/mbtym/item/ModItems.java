package dev.oxydien.mbtym.item;

import dev.oxydien.mbtym.InitMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class ModItems {

	public  static final Item FINGER = registerItem("finger", new FingerItem(new QuiltItemSettings()));
	public  static final Item SPIN = registerItem("spin", new SpinItem(new QuiltItemSettings().maxCount(128)));

	private static Item registerItem(String name, Item item)	 {
		final Item NewItem = Registry.register(Registries.ITEM, new Identifier(InitMod.MOD_ID, name), item);
		ItemGroupEvents.modifyEntriesEvent(RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(InitMod.MOD_ID,"mbtym")))
			.register(content -> {
			content.addItem(NewItem);
		});
		return NewItem;
	}
	public static void registerModItems() {
		InitMod.LOGGER.info("Registering items!");
	}
}

package dev.oxydien.mbtym.item;

import dev.oxydien.mbtym.gui.DebugScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class DebugItem extends Item {
	public DebugItem(Settings settings) {
		super(settings);
	}

	@ClientOnly
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (world.isClient()) {
			MinecraftClient.getInstance().setScreen(new DebugScreen());
		}
		return TypedActionResult.pass(user.getStackInHand(hand));
	}
}

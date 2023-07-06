package dev.oxydien.mbtym.item;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.gui.DebugScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.nio.charset.MalformedInputException;
import java.util.Objects;

public class DebugItem extends Item {
	public DebugItem(Settings settings) {
		super(settings);
	}

	private boolean change = true;


	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (world.isClient()) {
			MinecraftClient.getInstance().setScreen(new DebugScreen());
		}
		return TypedActionResult.pass(user.getStackInHand(hand));
	}
}

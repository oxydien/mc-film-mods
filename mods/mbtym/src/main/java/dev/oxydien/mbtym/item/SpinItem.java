package dev.oxydien.mbtym.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class SpinItem extends Item {
	public SpinItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		if (world.isClient) {
			float targetYaw = playerEntity.getYaw() + 25.0f;
			if (targetYaw > 360.0f) {
				targetYaw -= 360.0f;
			}

			float deltaYaw = targetYaw - playerEntity.getYaw();
			float step = deltaYaw / getMaxUseTime(playerEntity.getStackInHand(hand));

			rotateSmoothly(playerEntity, targetYaw, step);
		}

		return TypedActionResult.success(playerEntity.getStackInHand(hand));
	}

	private void rotateSmoothly(PlayerEntity playerEntity, float targetYaw, float step) {
		while (Math.abs(playerEntity.getYaw() - targetYaw) > Math.abs(step)) {
			playerEntity.setYaw(playerEntity.getYaw() + step);
		}

		playerEntity.setYaw(targetYaw);
	}
}

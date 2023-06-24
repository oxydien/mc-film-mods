package dev.oxydien.mbtym.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class FingerItem extends Item {
	public FingerItem(Item.Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		if (!world.isClient) {
			HitResult hitResult = playerEntity.raycast(20.0, 0.0F, false);

			if (hitResult.getType() == HitResult.Type.ENTITY) {
				EntityHitResult entityHitResult = (EntityHitResult) hitResult;
				Entity targetEntity = entityHitResult.getEntity();

				if (targetEntity instanceof PlayerEntity targetPlEntity) {
					playerEntity.sendMessage(Text.of("Hit player: " + targetPlEntity.getDisplayName().getString()), false);
				}
				else {
					playerEntity.sendMessage(Text.of("Hit entity: " + targetEntity.getEntityName()), false);
				}
			} else if (hitResult.getType() == HitResult.Type.BLOCK) {
				// Handle block hit
				playerEntity.sendMessage(Text.of("Hit block: " + hitResult.getPos().toString()), false);
			} else if (hitResult.getType() == HitResult.Type.MISS) {
				// Handle miss
				playerEntity.sendMessage(Text.of("You!"), false);
			}
		}

		return TypedActionResult.success(playerEntity.getStackInHand(hand));
	}
}

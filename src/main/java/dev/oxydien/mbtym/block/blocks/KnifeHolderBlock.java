package dev.oxydien.mbtym.block.blocks;

import dev.oxydien.mbtym.block.ModBlocks;
import dev.oxydien.mbtym.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class KnifeHolderBlock extends Block {
	public static final IntProperty KnifeNumber = IntProperty.of("knife_number", 0, 3);
	public static Boolean changed = false;
	public static final KnifeHolderBlock KNIFE_HOLDER_BLOCK = new KnifeHolderBlock(QuiltBlockSettings.copyOf(Blocks.OAK_WOOD));

	public KnifeHolderBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(KnifeNumber,0));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		changed = !changed;
		if (!world.isClient() && changed) {
			if (player.getStackInHand(hand).getItem() == ModItems.KNIFE_ITEM && state.get(KnifeNumber) < 3) {
				player.getStackInHand(hand).decrement(1);
				world.setBlockState(pos, state.with(KnifeNumber,Math.min(state.get(KnifeNumber) + 1, 3)));
				return super.onUse(state, world, pos, player, hand, hit);
			} else if (player.getStackInHand(hand).isEmpty() && state.get(KnifeNumber) != 0) {
				player.giveItemStack(new ItemStack(ModItems.KNIFE_ITEM));
				world.setBlockState(pos, state.with(KnifeNumber,Math.max(state.get(KnifeNumber) - 1, 0)));
				return super.onUse(state, world, pos, player, hand, hit);
			}
		}

		return super.onUse(state, world, pos, player, hand, hit);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(KnifeNumber);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return  VoxelShapes.cuboid(0.25f, 0.0f, 0.25f, 0.70f, 0.3f, 0.70f);
	}
}

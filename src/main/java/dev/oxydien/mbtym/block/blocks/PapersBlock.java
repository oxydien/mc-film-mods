package dev.oxydien.mbtym.block.blocks;

import dev.oxydien.mbtym.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class PapersBlock extends BookBlock{

	public PapersBlock(Settings settings) {
		super(settings);
	}

	public static int MAX_PAPER_COUNT = 6;
	public static final IntProperty PAPERCOUNT = IntProperty.of("paper_count",1,MAX_PAPER_COUNT);
	public static final BookBlock PAPERS_BLOCK = new PapersBlock(QuiltBlockSettings.copyOf(Blocks.STONE));

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder.add(PAPERCOUNT));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			ItemStack heldItem = player.getStackInHand(hand);
			if (heldItem.isEmpty()) {
				int paperCount = state.get(PAPERCOUNT);
				if (paperCount == 1) {
					world.removeBlock(pos, false); // Remove the block
				} else {
					paperCount = Math.max(paperCount - 1, 1);
					world.setBlockState(pos, state.with(PAPERCOUNT, paperCount));
				}
				player.giveItemStack(new ItemStack(ModBlocks.PAPERS_BLOCK.asItem()));
				return ActionResult.PASS;
			} else if (heldItem.getItem() == ModBlocks.PAPERS_BLOCK.asItem()) {
				int paperCount = state.get(PAPERCOUNT);
				if (paperCount < MAX_PAPER_COUNT) {
					paperCount = Math.min(paperCount + 1, MAX_PAPER_COUNT);
					world.setBlockState(pos, state.with(PAPERCOUNT, paperCount));

					if (!player.isCreative()) {
						heldItem.decrement(1); // Remove one from the held item
					}
					return ActionResult.SUCCESS;
				}
			}
		}
		return ActionResult.PASS;
	}
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		int paperCount = state.get(PAPERCOUNT);
		float height = BASE_HEIGHT + (paperCount - 1) * HEIGHT_INCREMENT;
		return VoxelShapes.cuboid(0.25f, 0.0f, 0.25f, 0.70f, height, 0.70f);
	}
}

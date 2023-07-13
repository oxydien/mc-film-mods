package dev.oxydien.mbtym.block.blocks;

import dev.oxydien.mbtym.InitMod;
import dev.oxydien.mbtym.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import net.minecraft.world.WorldView;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class BookBlock extends Block {
	public static final IntProperty BOOK_COUNT = IntProperty.of("book_count",1,3);
	public static final float HEIGHT_INCREMENT = 0.0625f;
	public static final float BASE_HEIGHT = 0.125f;
	public BookBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(BOOK_COUNT, 1));
	}

	public static final BookBlock BOOK_BLOCK = new BookBlock(QuiltBlockSettings.copyOf(Blocks.STONE));
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient()) {
			ItemStack heldItem = player.getStackInHand(hand);
			if (heldItem.isEmpty()) {
				int bookCount = state.get(BOOK_COUNT);
				if (bookCount == 1) {
					world.removeBlock(pos, false); // Remove the block
				} else {
					bookCount = Math.max(bookCount - 1, 1);
					world.setBlockState(pos, state.with(BOOK_COUNT, bookCount));
				}
				player.giveItemStack(new ItemStack(ModBlocks.BOOK_BLOCK.asItem()));
				return ActionResult.PASS;
			} else if (heldItem.getItem() == ModBlocks.BOOK_BLOCK.asItem()) {
				int bookCount = state.get(BOOK_COUNT);
				if (bookCount < 3) {
					bookCount = Math.min(bookCount + 1, 3);
					world.setBlockState(pos, state.with(BOOK_COUNT, bookCount));

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
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(BOOK_COUNT);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		int bookCount = state.get(BOOK_COUNT);
		float height = BASE_HEIGHT + (bookCount - 1) * HEIGHT_INCREMENT;
		return VoxelShapes.cuboid(0.25f, 0.0f, 0.25f, 0.70f, height, 0.70f);
	}
}

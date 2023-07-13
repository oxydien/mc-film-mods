package dev.oxydien.mbtym.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class EarthBlock extends Block {
	public static final IntProperty ROTATION = IntProperty.of("rotation",0,3);
	public EarthBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(ROTATION, 0));
	}

	public static final EarthBlock EARTH_BLOCK = new EarthBlock(QuiltBlockSettings.copyOf(Blocks.STONE));
	@Override // OnClick()
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient()) {
			Integer Rotation = state.get(ROTATION) + 1;
			if (Rotation > 3) {
				Rotation = 0;
			}
			world.setBlockState(pos, state.with(ROTATION, Rotation));
		}
		return ActionResult.SUCCESS;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(ROTATION);
	}
}

package dev.oxydien.mbtym.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class TimerBlock extends Block{
	public TimerBlock(Settings settings) {
		super(settings);
	}

	public static final TimerBlock TIMER_BLOCK = new TimerBlock(QuiltBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque());

	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context){
		return VoxelShapes.cuboid(0.13770630368119416, 0, 0.13770630368119416, 0.8377063036811942, 0.3125, 0.8377063036811942);
	}
}

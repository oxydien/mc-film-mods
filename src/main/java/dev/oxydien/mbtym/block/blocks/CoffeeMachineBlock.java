package dev.oxydien.mbtym.block.blocks;

import com.google.common.collect.ImmutableMap;
import dev.oxydien.mbtym.block.ModBlocks;
import dev.oxydien.mbtym.block.entity.CoffeeMachineEntity;
import dev.oxydien.mbtym.item.ModItems;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class CoffeeMachineBlock extends BlockWithEntity {
	public CoffeeMachineBlock(Settings settings) {
		super(settings);
	}
	public static Boolean changed = false;

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CoffeeMachineEntity(pos, state);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		changed = !changed;
		if (!world.isClient() && changed){
			player.giveItemStack(new ItemStack(ModItems.CUP_OF_COFFEE));
		}
		return ActionResult.PASS;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(0.23f,0f,0.1f,0.77f,0.8f,0.93f);
	}
}

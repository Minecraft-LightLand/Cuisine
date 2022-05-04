package dev.xkmc.cuisine.content.tools.board;

import dev.xkmc.cuisine.content.tools.base.CuisineUtil;
import dev.xkmc.cuisine.init.registrate.CuisineBlocks;
import dev.xkmc.cuisine.init.registrate.CuisineItems;
import dev.xkmc.l2library.block.impl.BlockEntityBlockMethodImpl;
import dev.xkmc.l2library.block.mult.AttackBlockMethod;
import dev.xkmc.l2library.block.mult.OnClickBlockMethod;
import dev.xkmc.l2library.block.one.BlockEntityBlockMethod;
import dev.xkmc.l2library.block.one.ShapeBlockMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ChoppingBoardBlock implements ShapeBlockMethod, AttackBlockMethod, OnClickBlockMethod {


	public static final BlockEntityBlockMethod<ChoppingBoardBlockEntity> TE = new BlockEntityBlockMethodImpl<>(CuisineBlocks.TE_BOARD, ChoppingBoardBlockEntity.class);

	private static final VoxelShape SHAPE = Block.box(0D, 0D, 0D, 16.0D, 5.0D, 16.0D);

	@Nullable
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		return SHAPE;
	}


	@Override
	public boolean attack(BlockState blockState, Level level, BlockPos blockPos, Player player) {
		ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
		BlockEntity be = level.getBlockEntity(blockPos);
		if (be instanceof ChoppingBoardBlockEntity te && stack.getItem() == CuisineItems.KNIFE.get()) {
			if (te.leftClick()) {
				CuisineUtil.hurtAndBreak(player, stack, InteractionHand.MAIN_HAND);
				return true;
			}
		}
		return false;
	}

	@Override
	public InteractionResult onClick(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
		BlockEntity be = level.getBlockEntity(blockPos);
		if (be instanceof ChoppingBoardBlockEntity te && stack.getItem() == CuisineItems.KNIFE.get()) {
			if (te.rightClick()) {
				CuisineUtil.hurtAndBreak(player, stack, InteractionHand.MAIN_HAND);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}

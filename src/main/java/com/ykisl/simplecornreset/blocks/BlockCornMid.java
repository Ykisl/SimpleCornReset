package com.ykisl.simplecornreset.blocks;

import com.ykisl.simplecornreset.init.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockCornMid extends BlockCorn 
{
	private static final int maxAge = 2;
	
	public BlockCornMid() 
	{
		super();
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) 
	{
		return blockState.is(ModBlocks.CORN.get()) && isMaxAge(blockState);
	}
	
	@Override
	public BlockState getNextState() 
	{
		return ModBlocks.CORN_TOP.get().defaultBlockState();
	}
	
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) 
	{
		return SHAPES[blockState.getValue(getAgeProperty()) + 6];
	}
	
	@Override
	protected boolean checkFertile(BlockState blockState, Level level, BlockPos blockPos) 
	{
		var belowBlockState = level.getBlockState(blockPos.below());
		return super.checkFertile(belowBlockState, level, blockPos.below());
	}
	
	@Override
	public int getMaxAge() 
	{
		return maxAge;
	}
	
	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) 
	{
		super.performBonemeal(serverLevel, randomSource, blockPos, blockState);
	}
}

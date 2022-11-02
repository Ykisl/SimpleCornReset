package com.ykisl.cornharvest.blocks;

import com.ykisl.cornharvest.init.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

public class BlockCornTop extends BlockCorn
{
	private static final int maxAge = 1;
	
	public BlockCornTop() 
	{
		super();
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) 
	{
		return blockState.is(ModBlocks.CORN_MID.get()) && isMaxAge(blockState);
	}
	
	@Override
	public int getMaxAge() 
	{
		return maxAge;
	}
	
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) 
	{
		return SHAPES[blockState.getValue(getAgeProperty()) + 6];
	}
	
	@Override
	protected boolean checkFertile(BlockState blockState, Level level, BlockPos blockPos) 
	{
		var belowBlockState = level.getBlockState(blockPos.below(2));
		return super.checkFertile(belowBlockState, level, blockPos.below(2));
	}
	
	@Override
	public BlockState getNextState() 
	{
		return null;
	}
	
	@Override
	protected void preformGrowCrops(Level level, BlockPos blockPos, BlockState blockState, RandomSource randomSource, boolean isBonemeal)
	{		
		boolean canGrow = GetGrowRandom(isBonemeal, randomSource);
		if(!isMaxAge(blockState)) 
		{
			if(ForgeHooks.onCropsGrowPre(level, blockPos, blockState, canGrow)) 
			{			
				var cornBlock = (BlockCorn)ModBlocks.CORN.get();
				var cornMidBlock = (BlockCorn)ModBlocks.CORN_MID.get();
				
				level.setBlockAndUpdate(blockPos, getStateForAge(getAge(blockState) + 1));
				level.setBlockAndUpdate(blockPos.below(), cornMidBlock.getStateForAge(getAge(level.getBlockState(blockPos.below())) + 1));
				level.setBlockAndUpdate(blockPos.below(2), cornBlock.getStateForAge(getAge(level.getBlockState(blockPos.below(2))) + 1));
				
				ForgeHooks.onCropsGrowPost(level, blockPos, blockState);
			}
		}
	}
}

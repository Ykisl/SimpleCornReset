package com.ykisl.cornharvest.blocks;

import com.ykisl.cornharvest.init.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
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
	
	@Override
	protected InteractionResult Harvest(BlockState blockState, Level level, BlockPos blockPos) 
	{
		if(!CanHarvest(blockState, level, blockPos)) 
		{
			return InteractionResult.PASS;
		}
		
		level.destroyBlock(blockPos, true);
		level.destroyBlock(blockPos.below(), true);
		level.destroyBlock(blockPos.below(2), true);
		
		var cornBlock = (BlockCorn)ModBlocks.CORN.get();		
		var newBlockState = cornBlock.getStateForAge(0);
		level.setBlockAndUpdate(blockPos.below(2), newBlockState);
		
		return InteractionResult.sidedSuccess(level.isClientSide);
	}
	
	@Override
	protected boolean CanHarvest(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) 
	{
		if(getAge(blockState) < 1) 
		{
			return false;
		}
		
		var midBlock = blockGetter.getBlockState(blockPos.below());
		if(!midBlock.hasProperty(AGE)) 
		{
			return false;
		}
		
		if(getAge(midBlock) < 3)
		{
			return false;
		}
		
		var bottomBlock = blockGetter.getBlockState(blockPos.below(2));
		if(!bottomBlock.hasProperty(AGE)) 
		{
			return false;
		}
		
		if(getAge(bottomBlock) < 5)
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) 
	{
		if(CanHarvest(blockState, level, blockPos)) 
		{
			return;
		}
		
		var cornBottomBlock = (BlockCorn)ModBlocks.CORN.get();	
		var bottomBlockPos = blockPos.below(2);
		var bottomBlock = level.getBlockState(bottomBlockPos);
		if(bottomBlock.getBlock() instanceof BlockCorn && !cornBottomBlock.isMaxAge(bottomBlock)) 
		{
			cornBottomBlock.growCrops(level, bottomBlockPos, bottomBlock);
			return;
		}
		
		var cornMidBlock = (BlockCorn)ModBlocks.CORN_MID.get();	
		var midBlockPos = blockPos.below();
		var midBlock = level.getBlockState(midBlockPos);
		if(midBlock.getBlock() instanceof BlockCornMid && !cornMidBlock.isMaxAge(midBlock)) 
		{
			cornMidBlock.growCrops(level, midBlockPos, midBlock);
			return;
		}
		
		if(!isMaxAge(blockState)) 
		{
			growCrops(level, blockPos, blockState);
			return;
		}
	}
}

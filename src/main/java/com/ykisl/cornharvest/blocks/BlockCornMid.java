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
	protected InteractionResult Harvest(BlockState blockState, Level level, BlockPos blockPos) 
	{
		if(!CanHarvest(blockState, level, blockPos)) 
		{
			return InteractionResult.PASS;
		}
		
		level.destroyBlock(blockPos, true);
		level.destroyBlock(blockPos.above(), true);
		level.destroyBlock(blockPos.below(), true);
		
		var cornBlock = (BlockCorn)ModBlocks.CORN.get();		
		var newBlockState = cornBlock.getStateForAge(0);
		level.setBlockAndUpdate(blockPos.below(), newBlockState);
		
		return InteractionResult.sidedSuccess(level.isClientSide);
	}
	
	@Override
	protected boolean CanHarvest(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) 
	{
		if(getAge(blockState) < 3) 
		{
			return false;
		}
		
		var bottomBlock = blockGetter.getBlockState(blockPos.below());
		if(!bottomBlock.hasProperty(AGE)) 
		{
			return false;
		}
		
		if(getAge(bottomBlock) < 5)
		{
			return false;
		}
		
		var topBlock = blockGetter.getBlockState(blockPos.above());
		if(!topBlock.hasProperty(AGE)) 
		{
			return false;
		}
		
		if(getAge(topBlock) < 1)
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
		var bottomBlockPos = blockPos.below();
		var bottomBlock = level.getBlockState(bottomBlockPos);
		if(bottomBlock.getBlock() instanceof BlockCorn && !cornBottomBlock.isMaxAge(bottomBlock)) 
		{
			cornBottomBlock.growCrops(level, bottomBlockPos, bottomBlock);
			return;
		}
		
		if(!isMaxAge(blockState)) 
		{
			growCrops(level, blockPos, blockState);
			return;
		}
		
		var cornTopBlock = (BlockCorn)ModBlocks.CORN_TOP.get();	
		var topBlockPos = blockPos.above();
		var topBlock = level.getBlockState(topBlockPos);
		if(topBlock.getBlock() instanceof BlockCornTop && !cornTopBlock.isMaxAge(topBlock)) 
		{
			cornTopBlock.growCrops(level, topBlockPos, topBlock);
			return;
		}
	}
}

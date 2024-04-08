package com.ykisl.cornharvest.blocks;

import com.ykisl.cornharvest.config.CornHarvestConfig;
import com.ykisl.cornharvest.init.ModBlocks;
import com.ykisl.cornharvest.init.ModItems;

import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

public class BlockCorn extends CropBlock
{
	private static final float friction = 0.3f;
	private static final float explosionResistance = 0.0f;
	private static final int maxAge = 4;
	
	public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
	
	public BlockCorn() 
	{
		super(Block.Properties.of().strength(friction, explosionResistance).sound(SoundType.CROP).noCollission());
	}
	
	protected static final VoxelShape[] SHAPES = new VoxelShape[] { 
		      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), 
		      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), 
		      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), 
		      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), 
		      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), 
		      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), 
		      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), 
		      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), 
		      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), 
		      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), 
		      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), 
		      Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D) };
	
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) 
	{
		return SHAPES[blockState.getValue(getAgeProperty())];
	}
	
	@Override
	public int getMaxAge() 
	{
		return maxAge;
	}
	
	@Override
	public IntegerProperty getAgeProperty() 
	{
		return AGE;
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) 
	{
		builder.add(AGE);
	}
	
	@Override
	protected ItemLike getBaseSeedId() 
	{
		return ModItems.KERNELS.get();
	}
	
	@Override
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) 
	{
		updateTick(serverLevel, blockPos, blockState, randomSource);
	}
	
	@Override
	public void growCrops(Level level, BlockPos blockPos, BlockState blockState) 
	{
		var randomSource = level.getRandom();
		preformGrowCrops(level, blockPos, blockState, randomSource, true);
	}
	
	protected void preformGrowCrops(Level level, BlockPos blockPos, BlockState blockState, RandomSource randomSource, boolean isBonemeal) 
	{
		boolean canGrow = GetGrowRandom(isBonemeal, randomSource);
		if(!isMaxAge(blockState)) 
		{
			if(ForgeHooks.onCropsGrowPre(level, blockPos, blockState, canGrow)) 
			{		
				var currentAge = getAge(blockState);
				var newState = getStateForAge(currentAge + 1);
				level.setBlock(blockPos, newState, 2);
				
				ForgeHooks.onCropsGrowPost(level, blockPos, blockState);
				
				var nextState = getNextState();
				var posBlockState = level.getBlockState(blockPos);
				if(posBlockState.hasProperty(AGE) && isMaxAge(posBlockState) && nextState != null) 
				{
					var newPos = blockPos.above();
					var newBlockState = level.getBlockState(newPos);
					
					var canBeReplaced = newBlockState.canBeReplaced();
					if(canBeReplaced && ForgeHooks.onCropsGrowPre(level, newPos, nextState, canGrow)) 
					{
						level.setBlock(newPos, nextState, 2);
						ForgeHooks.onCropsGrowPost(level, newPos, nextState);
					}
				}
			}
		}
		else 
		{
			var nextState = getNextState();
			if(nextState != null) 
			{
				var newPos = blockPos.above();
				var newBlockState = level.getBlockState(newPos);
				
				var canBeReplaced = newBlockState.canBeReplaced();
				if(canBeReplaced && ForgeHooks.onCropsGrowPre(level, newPos, nextState, canGrow)) 
				{
					level.setBlock(newPos, nextState, 2);
					ForgeHooks.onCropsGrowPost(level, newPos, nextState);
				}
			}
		}
	} 
	
	protected boolean checkFertile(BlockState blockState, Level level, BlockPos blockPos) 
	{
		var newBlockState = level.getBlockState(blockPos.below());
		return newBlockState.getBlock().isFertile(newBlockState, level, blockPos.below());
	}
	
	@Override
	protected int getBonemealAgeIncrease(Level level) 
	{
		return 1;
	}
	
	@Override
	public boolean isValidBonemealTarget(LevelReader reader, BlockPos blockPos, BlockState blockState, boolean isClient) 
	{
		return isValidPlantBonemeal(reader, blockPos, blockState);
	}
	
	@Override
	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) 
	{
		return isValidPlantBonemeal(level, blockPos, level.getBlockState(blockPos));
	}
	
	public BlockState getNextState() 
	{
		return ModBlocks.CORN_MID.get().defaultBlockState();
	}
	
	@SuppressWarnings("deprecation")
	private void updateTick(Level level, BlockPos blockPos, BlockState blockState, RandomSource randomSource) 
	{
		if(level.getBlockState(blockPos) == blockState) 
		{
			if(!level.isAreaLoaded(blockPos, 1)) 
			{
				return;
			}
			
			if(level.getRawBrightness(blockPos, 0) >= 9 && checkFertile(blockState, level, blockPos)) 
			{
				preformGrowCrops(level, blockPos, blockState, randomSource, false);
			}
		}
	}
	
	@Override
	public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) 
	{
		var abovePlaceOn = true;
		
		var nextState = getNextState();
		if(nextState != null && blockState.is(this) && isMaxAge((blockState))) 
		{
			var stateAbove = levelReader.getBlockState(blockPos.above());
			abovePlaceOn = stateAbove.is(nextState.getBlock());
		}
		
		return super.canSurvive(blockState, levelReader, blockPos) && abovePlaceOn;
	}
	
	protected boolean GetGrowRandom(boolean isBonemeal, RandomSource randomSource) 
	{
		if(isBonemeal) 
		{
			return randomSource.nextInt(2) == 0;
		}
		
		return randomSource.nextInt(10) == 0;
	}
	
	protected boolean isValidPlantBonemeal(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) 
	{
		if(CanHarvest(blockState, blockGetter, blockPos)) 
		{
			return false;
		}		
		
		var isCornAbove = blockGetter.getBlockState(blockPos.above()).getBlock() instanceof BlockCorn;	
		return isCornAbove || blockGetter.getBlockState(blockPos.above()).canBeReplaced();
	}

	protected boolean IsEasyHarvesting() 
	{
		return CornHarvestConfig.IS_EASY_HARVESTING.get();
	}
	
	protected boolean CanHarvest(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) 
	{
		if(getAge(blockState) < 5) 
		{
			return false;
		}
		
		var midBlock = blockGetter.getBlockState(blockPos.above());
		if(!midBlock.hasProperty(AGE)) 
		{
			return false;
		}
		
		if(getAge(midBlock) < 3)
		{
			return false;
		}
		
		var topBlock = blockGetter.getBlockState(blockPos.above(2));
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
	
	protected InteractionResult Harvest(BlockState blockState, Level level, BlockPos blockPos) 
	{
		if(!CanHarvest(blockState, level, blockPos)) 
		{
			return InteractionResult.PASS;
		}
		
		level.destroyBlock(blockPos, true);
		level.destroyBlock(blockPos.above(), true);
		level.destroyBlock(blockPos.above(2), true);
		
		var newBlockState = getStateForAge(0);
		level.setBlockAndUpdate(blockPos, newBlockState);
		
		return InteractionResult.sidedSuccess(level.isClientSide);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player,
			InteractionHand interactionHand, BlockHitResult blockHitResult) 
	{
		if(IsEasyHarvesting()) 
		{
			var canHarvest = CanHarvest(blockState, level, blockPos);			
			if(canHarvest) 
			{
				return Harvest(blockState, level, blockPos);
			}
		}
	
		return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
	}
	
	@Override
	public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) 
	{
		if(CanHarvest(blockState, level, blockPos)) 
		{
			return;
		}
		
		if(!isMaxAge(blockState)) 
		{
			growCrops(level, blockPos, blockState);
			return;
		}
		
		var cornMidBlock = (BlockCorn)ModBlocks.CORN_MID.get();	
		var midBlockPos = blockPos.above();
		var midBlock = level.getBlockState(midBlockPos);
		if(midBlock.getBlock() instanceof BlockCornMid && !cornMidBlock.isMaxAge(midBlock)) 
		{
			cornMidBlock.growCrops(level, midBlockPos, midBlock);
			return;
		}
		
		var cornTopBlock = (BlockCorn)ModBlocks.CORN_TOP.get();	
		var topBlockPos = blockPos.above(2);
		var topBlock = level.getBlockState(topBlockPos);
		if(topBlock.getBlock() instanceof BlockCornTop && !cornTopBlock.isMaxAge(topBlock)) 
		{
			cornTopBlock.growCrops(level, topBlockPos, topBlock);
			return;
		}
	}
}

package com.ykisl.cornharvest.items;

import com.ykisl.cornharvest.init.ModBlocks;
import com.ykisl.cornharvest.init.ModItems;
import com.ykisl.cornharvest.interfaces.IAnimalFoodItem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class ItemKernels extends ItemNameBlockItem implements IPlantable, IAnimalFoodItem
{

	private static int animalFoodGoalPriority = 3;
    private static double animalFoodSpeedModifier = 1.0D;
	
	public ItemKernels() 
	{
		super(ModBlocks.CORN.get(), new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
	}

	@Override
	public BlockState getPlant(BlockGetter level, BlockPos pos) 
	{
		return ModBlocks.CORN.get().defaultBlockState();
	}

	@Override
	public PlantType getPlantType(BlockGetter level, BlockPos pos) 
	{
		return PlantType.CROP;
	}
	
	@Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) 
    {
    	return feedMob(itemStack, player, livingEntity, interactionHand);
    }
	
	@Override
    public boolean isValidAnimal(Animal entity) 
    {
    	return entity instanceof Chicken;
    }
    
    @Override
    public int getAnimalFoodGoalPriority() 
    {
    	return animalFoodGoalPriority;
    }
    
    @Override
    public double getAnimalFoodSpeedModifier()
    {
    	return animalFoodSpeedModifier;
    }
    
    @Override
    public ItemLike getSelfItemInstance()
    {
    	return ModItems.KERNELS.get();
    }
}

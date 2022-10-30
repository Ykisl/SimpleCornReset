package com.ykisl.simplecornreset.interfaces;


import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

public interface IAnimalFoodItem 
{
	default public boolean isValidAnimal(Animal entity) 
	{
		return true;
	}
	
	default public InteractionResult feedMob(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) 
    {
    	if(livingEntity instanceof Animal animal) 
    	{
    		if(!isValidAnimal(animal)) 
    		{
    			return InteractionResult.PASS;
    		}
    		
        	var age = animal.getAge();
        	
        	if(!animal.level.isClientSide && age == 0 &&  animal.canFallInLove())
        	{
        		useItem(player, interactionHand, itemStack);
        		animal.setInLove(player);
        		return InteractionResult.SUCCESS;
        	}
        	
        	if(animal.isBaby()) 
        	{
        		useItem(player, interactionHand, itemStack);
        		animal.ageUp(Animal.getSpeedUpSecondsWhenFeeding(-age), true);
        		return InteractionResult.sidedSuccess(animal.level.isClientSide);
        	}
        	
        	if(animal.level.isClientSide) 
        	{
        		return InteractionResult.CONSUME;
        	}
    	}
    	
    	return InteractionResult.PASS;
    }
	
	private static void useItem(Player player, InteractionHand interactionHand, ItemStack itemStack) 
    {
    	if(!player.getAbilities().instabuild) 
    	{
    		itemStack.shrink(1);
    	}
    }
	
	public default void EntityJoinWorldSetup(EntityJoinLevelEvent entityJoinLevelEvent) 
	{
		var entity = entityJoinLevelEvent.getEntity();
		
		if(entity instanceof Animal animal) 
    	{
        	if(isValidAnimal(animal)) 
        	{
        		var goalPriority = getAnimalFoodGoalPriority();
        		var speedModifier = getAnimalFoodSpeedModifier();
        		
        		AddAnimalFoodGoal(animal, goalPriority, speedModifier);
        	}
    	}
		
	}
	
	default public void AddAnimalFoodGoal(Animal animal, int goalPriority, Double speedModifier) 
    {
    	var thisItem = getSelfItemInstance();
    	
    	var ingredient = Ingredient.of(thisItem);
    	var foodGoal = new TemptGoal(animal, speedModifier, ingredient, false);
    	
    	animal.goalSelector.addGoal(goalPriority, foodGoal);
    }
    
	public default int getAnimalFoodGoalPriority() 
    {
    	return 1;
    }
    
	public default double getAnimalFoodSpeedModifier() 
    {
		return 1.2D;
    }
    
	public default ItemLike getSelfItemInstance() 
    {
    	return null;
    }
}

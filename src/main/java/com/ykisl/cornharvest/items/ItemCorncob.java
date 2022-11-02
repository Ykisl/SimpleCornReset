package com.ykisl.cornharvest.items;

import com.ykisl.cornharvest.init.ModItems;
import com.ykisl.cornharvest.interfaces.IAnimalFoodItem;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ItemCorncob extends Item implements IAnimalFoodItem
{
	private static float saturation = 0.3f;
    private static int nutrition = 1;
    
    private static int animalFoodGoalPriority = 4;
    private static double animalFoodSpeedModifier = 1.2D;
	
    public ItemCorncob() 
    {
		super(GetItemProperties());
	}

    public static Item.Properties GetItemProperties()
    {
        var itemProperties = new Item.Properties();
        itemProperties.tab(CreativeModeTab.TAB_FOOD);

        var foodPropertiesBuilder = new FoodProperties.Builder();
        foodPropertiesBuilder.saturationMod(saturation);
        foodPropertiesBuilder.nutrition(nutrition);

        var foodProperties = foodPropertiesBuilder.build();
        itemProperties.food(foodProperties);

        return itemProperties;
    }
    
    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) 
    {
    	return feedMob(itemStack, player, livingEntity, interactionHand);
    }
    
    @Override
    public boolean isValidAnimal(Animal entity) 
    {
    	return entity instanceof Pig;
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
    	return ModItems.CORNCOB.get();
    }
}

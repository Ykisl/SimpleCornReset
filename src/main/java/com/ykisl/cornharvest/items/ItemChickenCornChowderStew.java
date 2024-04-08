package com.ykisl.cornharvest.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;

public class ItemChickenCornChowderStew extends BowlFoodItem
{
	private static float saturation = 0.6f;
    private static int nutrition = 10;
	
	public ItemChickenCornChowderStew() 
	{
		super(GetItemProperties());
	}
	
	public static Item.Properties GetItemProperties()
    {
        var itemProperties = new Item.Properties();
        //itemProperties.tab(CreativeModeTab.TAB_FOOD);
        itemProperties.stacksTo(1);

        var foodPropertiesBuilder = new FoodProperties.Builder();
        foodPropertiesBuilder.saturationMod(saturation);
        foodPropertiesBuilder.nutrition(nutrition);

        var foodProperties = foodPropertiesBuilder.build();
        itemProperties.food(foodProperties);

        return itemProperties;
    }
}

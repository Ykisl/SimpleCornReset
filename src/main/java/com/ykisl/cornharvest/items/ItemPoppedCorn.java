package com.ykisl.cornharvest.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class ItemPoppedCorn 
{
	private static float saturation = 0.1f;
    private static int nutrition = 1;
    
    public static Item GetItem()
    {
        var itemProperties = new Item.Properties();
        //itemProperties.tab(CreativeModeTab.TAB_FOOD);

        var foodPropertiesBuilder = new FoodProperties.Builder();
        foodPropertiesBuilder.saturationMod(saturation);
        foodPropertiesBuilder.nutrition(nutrition);
        foodPropertiesBuilder.fast();

        var foodProperties = foodPropertiesBuilder.build();
        itemProperties.food(foodProperties);

        var item = new Item(itemProperties);
        return item;
    }
}

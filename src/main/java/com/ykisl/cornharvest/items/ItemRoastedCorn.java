package com.ykisl.cornharvest.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class ItemRoastedCorn 
{
	private static float saturation = 0.6f;
    private static int nutrition = 6;
    
    public static Item GetItem()
    {
        var itemProperties = new Item.Properties();
        //itemProperties.tab(CreativeModeTab.TAB_FOOD);

        var foodPropertiesBuilder = new FoodProperties.Builder();
        foodPropertiesBuilder.saturationMod(saturation);
        foodPropertiesBuilder.nutrition(nutrition);

        var foodProperties = foodPropertiesBuilder.build();
        itemProperties.food(foodProperties);

        var item = new Item(itemProperties);
        return item;
    }
}

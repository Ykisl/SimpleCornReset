package com.ykisl.cornharvest.items;

import com.ykisl.cornharvest.init.ModItems;
import com.ykisl.cornharvest.interfaces.IAnimalFoodItem;
import com.ykisl.cornharvest.interfaces.IVilagerTradable;

import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.village.VillagerTradesEvent;

public class ItemCorncob extends Item implements IAnimalFoodItem, IVilagerTradable
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
        //itemProperties.tab(CreativeModeTab.TAB_FOOD);

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

	@Override
	public void VilagerTradeSetup(VillagerTradesEvent event) 
	{
		if(event.getType() == VillagerProfession.FARMER) 
		{
			var trades = event.getTrades();
			
			if(trades.size() <= 0) 
			{
				return;
			}
			
			var vilagerLevel = 1;
			trades.get(vilagerLevel).add((trader, rand) -> GetSellMerchantOffer(trader, rand));
		}
	}
	
	private MerchantOffer GetSellMerchantOffer(Entity trader, RandomSource randomSource) 
	{
		if(trader instanceof Villager villager) 
		{
			var villagerType = villager.getVillagerData().getType();
			if(villagerType != VillagerType.DESERT && villagerType != VillagerType.PLAINS) 
			{
				return null;
			}
			
			var priceItemStack = new ItemStack(getSelfItemInstance(), 25 + randomSource.nextInt(0, 2));
			var itemStack = new ItemStack(Items.EMERALD, 1);
			
			return new MerchantOffer(priceItemStack, itemStack, 10, 3, 0.02f);
		}		
		
		return null;
	}
}

package com.ykisl.cornharvest.init;

import com.ykisl.cornharvest.CornHarvest;
import com.ykisl.cornharvest.interfaces.IAnimalFoodItem;
import com.ykisl.cornharvest.interfaces.IVilagerTradable;
import com.ykisl.cornharvest.items.ItemChickenCornChowderStew;
import com.ykisl.cornharvest.items.ItemCornChowderStew;
import com.ykisl.cornharvest.items.ItemCorncob;
import com.ykisl.cornharvest.items.ItemKernels;
import com.ykisl.cornharvest.items.ItemPoppedCorn;
import com.ykisl.cornharvest.items.ItemRoastedCorn;
import com.ykisl.cornharvest.utils.ImmutableUtils;

import net.minecraft.world.entity.ai.behavior.WorkAtComposter;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    private static final DeferredRegister<Item> MOD_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CornHarvest.MODID);

    public static final RegistryObject<Item> CORNCOB = MOD_ITEMS.register("corncob", () -> new ItemCorncob());
    public static final RegistryObject<Item> KERNELS = MOD_ITEMS.register("kernels",  () -> new ItemKernels());
    public static final RegistryObject<Item> ROASTEDCORN = MOD_ITEMS.register("roastedcorn", () -> ItemRoastedCorn.GetItem());
    public static final RegistryObject<Item> POPPEDCORN = MOD_ITEMS.register("poppedcorn", () -> ItemPoppedCorn.GetItem());
    public static final RegistryObject<Item> CORNCHOWDER = MOD_ITEMS.register("cornchowder", () -> new ItemCornChowderStew());
    public static final RegistryObject<Item> CHICKENCORNCHOWDER = MOD_ITEMS.register("chickencornchowder", () -> new ItemChickenCornChowderStew());

    public void Register(final IEventBus modEventBus)
    {
        MOD_ITEMS.register(modEventBus);
    }

    public void Setup(final FMLCommonSetupEvent event)
    {
    	ComposterBlock.COMPOSTABLES.put(CORNCOB.get(), 0.65F);
    	ComposterBlock.COMPOSTABLES.put(KERNELS.get(), 0.3F);
    	
    	WorkAtComposter.COMPOSTABLE_ITEMS = ImmutableUtils.AppendElement(WorkAtComposter.COMPOSTABLE_ITEMS, CORNCOB.get());
    	WorkAtComposter.COMPOSTABLE_ITEMS = ImmutableUtils.AppendElement(WorkAtComposter.COMPOSTABLE_ITEMS, KERNELS.get());
    	
    	Villager.WANTED_ITEMS = ImmutableUtils.AppendElement(Villager.WANTED_ITEMS, CORNCOB.get());
    	Villager.WANTED_ITEMS = ImmutableUtils.AppendElement(Villager.WANTED_ITEMS, KERNELS.get());
    }
    
    public static void ClientSetup(FMLClientSetupEvent event) 
	{
		
	}
    
    public static void EntityJoinWorldSetup(EntityJoinLevelEvent entityJoinLevelEvent)
    {
    	((IAnimalFoodItem)CORNCOB.get()).EntityJoinWorldSetup(entityJoinLevelEvent);
    	((IAnimalFoodItem)KERNELS.get()).EntityJoinWorldSetup(entityJoinLevelEvent);
    }
    
    public static void VilagerTradeSetup(VillagerTradesEvent event) 
    {
		((IVilagerTradable)CORNCOB.get()).VilagerTradeSetup(event);
	}
}

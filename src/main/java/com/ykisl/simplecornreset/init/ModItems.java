package com.ykisl.simplecornreset.init;

import com.ykisl.simplecornreset.SimpleCornReset;
import com.ykisl.simplecornreset.interfaces.IAnimalFoodItem;
import com.ykisl.simplecornreset.items.ItemChickenCornChowderStew;
import com.ykisl.simplecornreset.items.ItemCornChowderStew;
import com.ykisl.simplecornreset.items.ItemCorncob;
import com.ykisl.simplecornreset.items.ItemKernels;
import com.ykisl.simplecornreset.items.ItemPoppedCorn;
import com.ykisl.simplecornreset.items.ItemRoastedCorn;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    private static final DeferredRegister<Item> MOD_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimpleCornReset.MODID);

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
    }
    
    public static void ClientSetup(FMLClientSetupEvent event) 
	{
		
	}
    
    public static void EntityJoinWorldSetup(EntityJoinLevelEvent entityJoinLevelEvent)
    {
    	((IAnimalFoodItem)CORNCOB.get()).EntityJoinWorldSetup(entityJoinLevelEvent);
    	((IAnimalFoodItem)KERNELS.get()).EntityJoinWorldSetup(entityJoinLevelEvent);
    }
}

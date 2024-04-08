package com.ykisl.cornharvest.events;

import javax.annotation.Nonnull;
import com.ykisl.cornharvest.CornHarvest;
import com.ykisl.cornharvest.init.ModItems;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = CornHarvest.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents 
{
	@SubscribeEvent
	public static void onBuildCreativeModeTab(BuildCreativeModeTabContentsEvent event) 
	{
		ModItems.BuildCreativeModeTab(event);
	}
}

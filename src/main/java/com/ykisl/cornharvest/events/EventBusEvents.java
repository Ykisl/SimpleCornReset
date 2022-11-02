package com.ykisl.cornharvest.events;

import com.ykisl.cornharvest.CornHarvest;
import com.ykisl.cornharvest.init.ModBlocks;
import com.ykisl.cornharvest.init.ModItems;
import com.ykisl.cornharvest.init.ModWorldGen;

import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CornHarvest.MODID)
public class EventBusEvents 
{
	@SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event)
    {
    }
	
	@SubscribeEvent
	public static void onServerAboutToStartEvent(final ServerAboutToStartEvent event) 
	{
		ModWorldGen.ServerAboutToStartSetup(event);
	}
	
	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinLevelEvent entityJoinLevelEvent) 
	{
		ModBlocks.EntityJoinWorldSetup(entityJoinLevelEvent);
		ModItems.EntityJoinWorldSetup(entityJoinLevelEvent);
	}
	
	@SubscribeEvent
	public static void onVilagerTradeEvent(VillagerTradesEvent event) 
	{
		ModItems.VilagerTradeSetup(event);
	}
}

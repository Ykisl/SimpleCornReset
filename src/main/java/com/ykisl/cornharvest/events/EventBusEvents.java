package com.ykisl.cornharvest.events;

import com.ykisl.cornharvest.CornHarvest;
import com.ykisl.cornharvest.init.ModBlocks;
import com.ykisl.cornharvest.init.ModItems;

import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CornHarvest.MODID)
public class EventBusEvents 
{
	@SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event)
    {
    }
	
	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinLevelEvent entityJoinLevelEvent) 
	{
		ModBlocks.EntityJoinWorldSetup(entityJoinLevelEvent);
		ModItems.EntityJoinWorldSetup(entityJoinLevelEvent);
	} 
}

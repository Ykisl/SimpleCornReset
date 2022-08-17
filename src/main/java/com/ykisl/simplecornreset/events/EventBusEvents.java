package com.ykisl.simplecornreset.events;

import com.ykisl.simplecornreset.SimpleCornReset;
import com.ykisl.simplecornreset.init.ModBlocks;
import com.ykisl.simplecornreset.init.ModItems;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimpleCornReset.MODID)
public class EventBusEvents 
{
	@SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event)
    {
    }
	
	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent entityJoinWorldEvent) 
	{
		ModBlocks.EntityJoinWorldSetup(entityJoinWorldEvent);
		ModItems.EntityJoinWorldSetup(entityJoinWorldEvent);
	} 
}

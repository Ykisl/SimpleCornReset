package com.ykisl.cornharvest.events;

import javax.annotation.Nonnull;
import com.ykisl.cornharvest.CornHarvest;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = CornHarvest.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents 
{
	@SubscribeEvent
	public static void registerModifierSerializers(@Nonnull final RegisterEvent registerEvent) 
	{
	}
}

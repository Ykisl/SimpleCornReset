package com.ykisl.simplecornreset.events;

import javax.annotation.Nonnull;

import com.ykisl.simplecornreset.SimpleCornReset;
import com.ykisl.simplecornreset.events.loot.KernelsFromGrassModifier;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = SimpleCornReset.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents 
{
	@SubscribeEvent
	public static void registerModifierSerializers(@Nonnull final RegisterEvent registerEvent) 
	{
		registerEvent.register(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, helper -> 
		{
			helper.register(new ResourceLocation(SimpleCornReset.MODID, "kernels_from_grass"), new KernelsFromGrassModifier.Serializer());
			helper.register(new ResourceLocation(SimpleCornReset.MODID, "kernels_from_tall_grass"), new KernelsFromGrassModifier.Serializer());
		});
		
		registerEvent.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, helper -> 
		{
		});
	}
}

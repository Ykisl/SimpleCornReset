package com.ykisl.simplecornreset.events;

import javax.annotation.Nonnull;

import com.mojang.serialization.Codec;
import com.ykisl.simplecornreset.SimpleCornReset;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.IGlobalLootModifier;
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
	}
}

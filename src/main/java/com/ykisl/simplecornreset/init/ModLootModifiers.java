package com.ykisl.simplecornreset.init;

import com.mojang.serialization.Codec;
import com.ykisl.simplecornreset.SimpleCornReset;
import com.ykisl.simplecornreset.loot.KernelsFromGrassModifier;
import com.ykisl.simplecornreset.loot.KernelsFromTallGrassModifier;

import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers 
{
	private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODOFIERS = 
			DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SimpleCornReset.MODID);
	
	public static final RegistryObject<Codec<? extends IGlobalLootModifier>> KERNELS_FROM_GRASS = 
			LOOT_MODOFIERS.register("kernels_from_grass", KernelsFromGrassModifier.CODEC);
	
	public static final RegistryObject<Codec<? extends IGlobalLootModifier>> KERNELS_FROM_TALL_GRASS = 
			LOOT_MODOFIERS.register("kernels_from_tall_grass", KernelsFromTallGrassModifier.CODEC);
	
	public void Register(final IEventBus modEventBus) 
	{
		LOOT_MODOFIERS.register(modEventBus);
	}
}

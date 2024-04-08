package com.ykisl.cornharvest.utils;

import java.util.ArrayList;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraftforge.event.server.ServerAboutToStartEvent;

public class StructureRegisterPool 
{
	private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = 
			ResourceKey.create(Registries.PROCESSOR_LIST, new ResourceLocation("minecraft", "empty"));
	
	protected Registry<StructureTemplatePool> templatePoolRegistry;
	protected Registry<StructureProcessorList> processorListRegistry;
	
	public StructureRegisterPool(final ServerAboutToStartEvent event) 
	{
		templatePoolRegistry= event.getServer()
				.registryAccess()
				.registry(Registries.TEMPLATE_POOL)
				.get();
		
		processorListRegistry = event.getServer()
				.registryAccess()
				.registry(Registries.PROCESSOR_LIST)
				.get();		
	}
	
	public void RegisterStructureToPool(ResourceLocation poolResourceLocation, ResourceLocation nbtResourceLocation, int weight) 
	{
		var emptyProcessorList = processorListRegistry.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);		
		
		var pool = templatePoolRegistry.get(poolResourceLocation);
		if (pool == null) 
		{
			return;
		}
		
		var piece = SinglePoolElement
				.single(nbtResourceLocation.toString(), emptyProcessorList)
				.apply(StructureTemplatePool.Projection.RIGID);
		
		for (int i = 0; i < weight; i++) 
		{
            pool.templates.add(piece);
        }
		
		var listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
		listOfPieceEntries.add(new Pair<>(piece, weight));
        pool.rawTemplates = listOfPieceEntries;
	} 
}

package com.ykisl.cornharvest.world.vilage;

import com.ykisl.cornharvest.CornHarvest;
import com.ykisl.cornharvest.utils.StructureRegisterPool;
import net.minecraft.resources.ResourceLocation;

public class VilageWorldGen 
{
	private static final ResourceLocation STRUCTURES_LOC_DESERT_VILAGE_HOUSES = 
			new ResourceLocation("village/desert/houses");
	
	private static final ResourceLocation STRUCTURES_LOC_PLAINS_VILAGE_HOUSES = 
			new ResourceLocation("village/plains/houses");
	
	private static final ResourceLocation STRUCTURE_DESERT_VILAGE_CORN_FARM = 
			new ResourceLocation(CornHarvest.MODID, "village/desert/houses/desert_corn_farm_1");
	
	private static final ResourceLocation STRUCTURE_PLAINS_VILAGE_SMALL_CORN_FARM = 
			new ResourceLocation(CornHarvest.MODID, "village/plains/houses/plains_small_corn_farm_1");
	
	private static final ResourceLocation STRUCTURE_PLAINS_VILAGE_LARGE_CORN_FARM = 
			new ResourceLocation(CornHarvest.MODID, "village/plains/houses/plains_large_cron_farm_1");
	
	public static void RegisterStructures(StructureRegisterPool structureRegisterPool) 
	{
		structureRegisterPool.RegisterStructureToPool(STRUCTURES_LOC_PLAINS_VILAGE_HOUSES, STRUCTURE_PLAINS_VILAGE_SMALL_CORN_FARM, 5);
		structureRegisterPool.RegisterStructureToPool(STRUCTURES_LOC_PLAINS_VILAGE_HOUSES, STRUCTURE_PLAINS_VILAGE_LARGE_CORN_FARM, 5);
		structureRegisterPool.RegisterStructureToPool(STRUCTURES_LOC_DESERT_VILAGE_HOUSES, STRUCTURE_DESERT_VILAGE_CORN_FARM, 5);
	}	
}

package com.ykisl.cornharvest.init;

import com.ykisl.cornharvest.utils.StructureRegisterPool;
import com.ykisl.cornharvest.world.vilage.VilageWorldGen;
import net.minecraftforge.event.server.ServerAboutToStartEvent;

public class ModWorldGen 
{
	public static void ServerAboutToStartSetup(final ServerAboutToStartEvent event) 
	{
		var structureRegisterPool = new StructureRegisterPool(event);		
		VilageWorldGen.RegisterStructures(structureRegisterPool);
	}
}

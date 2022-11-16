package com.ykisl.cornharvest.config;

import org.antlr.v4.parse.ANTLRParser.finallyClause_return;

import com.ykisl.cornharvest.CornHarvest;

import net.minecraftforge.common.ForgeConfigSpec;

public class CornHarvestConfig 
{
	public static final String CONFIG_FILE_NAME = CornHarvest.MODID + "-config.toml";
	
	public static final ForgeConfigSpec.Builder SPEC_BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	
	public static final ForgeConfigSpec.BooleanValue IS_EASY_HARVESTING;
	
	static 
	{
		SPEC_BUILDER.push("CornHarvest config");	
		SPEC_BUILDER.pop();
		
		IS_EASY_HARVESTING = SPEC_BUILDER.define("EasyHarvesting", true);
		
		SPEC = SPEC_BUILDER.build();
	}
}

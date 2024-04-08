package com.ykisl.cornharvest;

import com.mojang.logging.LogUtils;
import com.ykisl.cornharvest.config.CornHarvestConfig;
import com.ykisl.cornharvest.init.ModBlocks;
import com.ykisl.cornharvest.init.ModItems;
import com.ykisl.cornharvest.init.ModLootModifiers;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CornHarvest.MODID)
public class CornHarvest
{
    public static final String MODID = "cornharvest";
    private static final Logger LOGGER = LogUtils.getLogger();
    
    @SuppressWarnings("unused")
	private  static final ModLoadingContext modLoadingContext = ModLoadingContext.get();
    
    private ModItems modItems;
    private ModBlocks modBlocks;
    private ModLootModifiers modLootModifiers;

    public CornHarvest()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();   
        modEventBus.addListener(this::commonSetup);

        modItems = new ModItems();
        modBlocks = new ModBlocks();
        modLootModifiers = new ModLootModifiers();
        
        modItems.Register(modEventBus);
        modBlocks.Register(modEventBus);
        modLootModifiers.Register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CornHarvestConfig.SPEC, CornHarvestConfig.CONFIG_FILE_NAME);
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        modItems.Setup(event);
    }
    
    public static Logger GetLogger() 
    {
    	return LOGGER;
    }
}

package com.ykisl.simplecornreset;

import com.mojang.logging.LogUtils;
import com.ykisl.simplecornreset.init.ModBlocks;
import com.ykisl.simplecornreset.init.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(SimpleCornReset.MODID)
public class SimpleCornReset
{
    public static final String MODID = "simplecornreset";
    private static final Logger LOGGER = LogUtils.getLogger();
    
    @SuppressWarnings("unused")
	private  static final ModLoadingContext modLoadingContext = ModLoadingContext.get();
    
    private ModItems modItems;
    private ModBlocks modBlocks;

    public SimpleCornReset()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();   
        modEventBus.addListener(this::commonSetup);

        modItems = new ModItems();
        modBlocks = new ModBlocks();
        
        modItems.Register(modEventBus);
        modBlocks.Register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        modItems.Setup(event);
        modBlocks.Setup(event);
    }
    
    public static Logger GetLogger() 
    {
    	return LOGGER;
    }
    
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        	ModItems.ClientSetup(event);
        	ModBlocks.ClientSetup(event);
        }
    }
}

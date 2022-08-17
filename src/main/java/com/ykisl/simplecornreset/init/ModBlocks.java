package com.ykisl.simplecornreset.init;

import com.ykisl.simplecornreset.SimpleCornReset;
import com.ykisl.simplecornreset.blocks.BlockCorn;
import com.ykisl.simplecornreset.blocks.BlockCornMid;
import com.ykisl.simplecornreset.blocks.BlockCornTop;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks 
{
	private static final DeferredRegister<Block> MOD_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimpleCornReset.MODID);
	
	public static final RegistryObject<Block> CORN = MOD_BLOCKS.register("corn", () -> new BlockCorn());
	public static final RegistryObject<Block> CORN_MID = MOD_BLOCKS.register("corn_mid", () -> new BlockCornMid());
	public static final RegistryObject<Block> CORN_TOP = MOD_BLOCKS.register("corn_top", () -> new BlockCornTop());
	
	public void Register(final IEventBus modEventBus) 
	{
		MOD_BLOCKS.register(modEventBus);
	}
	
	public void Setup(final FMLCommonSetupEvent event)
    {

    }
	
	public static void ClientSetup(FMLClientSetupEvent event) 
	{
		ItemBlockRenderTypes.setRenderLayer(CORN.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(CORN_MID.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(CORN_TOP.get(), RenderType.cutout());
	}
	
	public static void EntityJoinWorldSetup(EntityJoinWorldEvent entityJoinWorldEvent) 
    {
    	
    }
}

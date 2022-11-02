package com.ykisl.cornharvest.loot;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ykisl.cornharvest.init.ModItems;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class KernelsFromGrassModifier extends LootModifier
{
	public static final Supplier<Codec<KernelsFromGrassModifier>> CODEC = Suppliers.memoize(() -> 
	{
		return RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, KernelsFromGrassModifier::new));
	});
	
	protected KernelsFromGrassModifier(LootItemCondition[] conditionsIn) 
	{
		super(conditionsIn);
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) 
	{
		generatedLoot.add(new ItemStack(ModItems.KERNELS.get(), 1));
		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() 
	{
		return CODEC.get();
	}
}

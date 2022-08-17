package com.ykisl.simplecornreset.events.loot;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.ykisl.simplecornreset.init.ModItems;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class KernelsFromGrassModifier extends LootModifier
{
	
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
	
	public static class Serializer extends GlobalLootModifierSerializer<KernelsFromGrassModifier>
	{

		@Override
		public KernelsFromGrassModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) 
		{		
			return new KernelsFromGrassModifier(ailootcondition);
		}

		@Override
		public JsonObject write(KernelsFromGrassModifier instance) 
		{
			var jsonObject = makeConditions(instance.conditions);	
			return jsonObject;
		}
		
	}
}

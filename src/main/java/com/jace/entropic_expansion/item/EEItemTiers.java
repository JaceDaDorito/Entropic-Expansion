package com.jace.entropic_expansion.item;

import com.google.common.base.Supplier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import com.jace.entropic_expansion.misc.EETagRegistry;

public class EEItemTiers{
	public static final Tier ROSEGOLD =  new SimpleTier(0, 250, 12.0F, 2.0F, 14, EETagRegistry.Blocks.NEEDS_ROSEGOLD_TOOL, () -> Ingredient.of(EETagRegistry.Items.ROSEGOLD_REPAIRING));
}
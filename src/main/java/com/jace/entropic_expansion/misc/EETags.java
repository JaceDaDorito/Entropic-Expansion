package com.jace.entropic_expansion.misc;

import com.jace.entropic_expansion.EntropicExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;

public class EETags {
	public static class Items{
		public static final TagKey<Item> ROSEGOLD_REPAIRING = tag("rosegold_repairing");
		
		private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(EntropicExpansion.MODID, name));
        }
	}
}

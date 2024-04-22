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

public class EETagRegistry {
	
	public static class Blocks{
		public static final TagKey<Block> NEEDS_ROSEGOLD_TOOL = registerBlockTag("needs_rosegold_tool");
		
		private static TagKey<Block> registerBlockTag(String name) {
	        return TagKey.create(Registries.BLOCK, new ResourceLocation(EntropicExpansion.MODID, name));
	    }
	}
	
	public static class Items{
		public static final TagKey<Item> ROSEGOLD_REPAIRING = registerItemTag("rosegold_repairing");

		private static TagKey<Item> registerItemTag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(EntropicExpansion.MODID, name));
        }
		
	}
}

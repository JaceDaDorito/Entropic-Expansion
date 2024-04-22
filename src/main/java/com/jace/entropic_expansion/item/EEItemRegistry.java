package com.jace.entropic_expansion.item;

import com.jace.entropic_expansion.EntropicExpansion;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

public class EEItemRegistry {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EntropicExpansion.MODID);
	
	public static final DeferredItem<Item> EE_DEBUG = ITEMS.register("ee_debug", EEDebugItem::new);
	
	public static final DeferredItem<Item> ROSEGOLD_AXE = ITEMS.register("rosegold_axe", () -> new AxeItem(EEItemTierRegistry.ROSEGOLD, 6.0F, -3.1F, new Item.Properties()));
	public static final DeferredItem<Item> ROSEGOLD_HOE = ITEMS.register("rosegold_hoe", () -> new HoeItem(EEItemTierRegistry.ROSEGOLD, -2, -1.0F, new Item.Properties()));
	public static final DeferredItem<Item> ROSEGOLD_PICKAXE = ITEMS.register("rosegold_pickaxe", () -> new PickaxeItem(EEItemTierRegistry.ROSEGOLD, 4, -2.8F, new Item.Properties()));
	public static final DeferredItem<Item> ROSEGOLD_SHOVEL = ITEMS.register("rosegold_shovel", () -> new ShovelItem(EEItemTierRegistry.ROSEGOLD, 1.5F, -3.0F, new Item.Properties()));
	
	public static final DeferredItem<Item> ROSEGOLD_INGOT = ITEMS.register("rosegold_ingot", () -> new Item(new Item.Properties()));
	
	public static void Init() {
		
	}
	
	public static void InitDispenser() {
		
	}
}

/*package com.jace.entropic_expansion.capability;

import com.jace.entropic_expansion.EntropicExpansion;
import com.jace.entropic_expansion.item.EEItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;

public class EECapabilityRegistry {
	private static void registerCapabilities(RegisterCapabilitiesEvent event) {
	    event.registerItem(
	        ITEM_DEBUG_HANDLER,
	        (itemStack, context) -> {return new EEDebugItemCapability(itemStack);},
	        EEItemRegistry.EE_DEBUG);
	}
	
	public static final ItemCapability<EEDebugItemCapability, Void> ITEM_DEBUG_HANDLER = ItemCapability.createVoid(
			new ResourceLocation(EntropicExpansion.MODID, "item_debug_handler"), EEDebugItemCapability.class);
}*/

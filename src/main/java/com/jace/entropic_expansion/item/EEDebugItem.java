package com.jace.entropic_expansion.item;

import java.util.Optional;
import javax.annotation.Nullable;

import com.jace.entropic_expansion.EntropicExpansion;
import com.jace.entropic_expansion.blockentity.TransceiverEntity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.items.IItemHandler;
import com.jace.entropic_expansion.block.Transceiver;

public class EEDebugItem extends Item{

	public EEDebugItem() {
		super(new Item.Properties().stacksTo(1));
	}
	
	public InteractionResult useOn(UseOnContext pContext) {
		Level level = pContext.getLevel();
		BlockPos blockpos = pContext.getClickedPos();
		ItemStack itemStack = pContext.getItemInHand();
		
		CompoundTag tag = itemStack.getOrCreateTag();
		if(level.getBlockEntity(blockpos) instanceof TransceiverEntity) {
	        if(!tag.contains("Pending") || !tag.getBoolean("Pending") ||
	        		(TransceiverEntity)level.getBlockEntity(BlockPos.of(tag.getLong("PendingPos"))) == null) {
	        	
	        	tag.putBoolean("Pending", true);
	        	tag.putLong("PendingPos", blockpos.asLong());
	        	
	        	EntropicExpansion.LOGGER.debug("Pending: " + blockpos.toString());
	        }
	        else {
	        	BlockPos pendingPos = BlockPos.of(tag.getLong("PendingPos"));
	        	TransceiverEntity pendingEntity = (TransceiverEntity)level.getBlockEntity(pendingPos);
	        	
	        	pendingEntity.receiver = blockpos;
	        	BlockState pendingReceiverState = level.getBlockState(pendingPos).setValue(Transceiver.HAS_RECEIVER, true);
	        	((TransceiverEntity)level.getBlockEntity(blockpos)).provider = pendingPos;
	        	BlockState currentProviderState = level.getBlockState(blockpos).setValue(Transceiver.HAS_PROVIDER, true);
	        	
	        	level.setBlockAndUpdate(pendingPos, pendingReceiverState);
	        	level.setBlockAndUpdate(blockpos, currentProviderState);
	        	
	        	tag.putBoolean("Pending", false);
				tag.remove("PendingPos");
				
				EntropicExpansion.LOGGER.debug("Connected: " + pendingPos.toString() + " to " + blockpos.toString());
	        }
		}
		else {
			if(tag.contains("Pending") && tag.getBoolean("Pending")){
				tag.putBoolean("Pending", false);
				tag.remove("PendingPos");
				EntropicExpansion.LOGGER.debug("Disconnected");
			}
		}

		return InteractionResult.SUCCESS;
	}

}

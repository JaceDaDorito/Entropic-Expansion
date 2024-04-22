package com.jace.entropic_expansion.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import javax.annotation.Nullable;

import com.jace.entropic_expansion.EntropicExpansion;
import com.jace.entropic_expansion.block.EEBlockRegistry;

public class TransceiverEntity extends BlockEntity{
	
	public BlockPos receiver;
	public BlockPos provider;
	private static final String TAG_RECEIVER = "receiver_pos";
    private static final String TAG_PROVIDER = "provider_pos";

	public TransceiverEntity(BlockPos pPos, BlockState pBlockState) {
		super(EEBlockEntityRegistry.TRANSCEIVER.get(), pPos, pBlockState);
	}
	
	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		this.receiver = BlockPos.of(pTag.getLong(TAG_RECEIVER));
		this.provider = BlockPos.of(pTag.getLong(TAG_PROVIDER));
	}
	
	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		storeBlockPos(pTag, TAG_RECEIVER, this.receiver);
		storeBlockPos(pTag, TAG_PROVIDER, this.provider);
	}
	
	private static void storeBlockPos(CompoundTag pTag, String pKey, @Nullable BlockPos pBlockPos) {
		if(pBlockPos != null) {
			pTag.putLong(pKey, pBlockPos.asLong());
		}
	}
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }
	
	public boolean hasProvider() {
		return provider != null;
	}
	
	public boolean hasReceiver() {
		return receiver != null;
	}
	
}

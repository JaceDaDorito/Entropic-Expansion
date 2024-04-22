package com.jace.entropic_expansion.block;

import javax.annotation.Nullable;

import com.jace.entropic_expansion.EntropicExpansion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BeaconBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.ticks.TickPriority;
import net.minecraft.world.level.block.state.BlockBehaviour;
import  com.jace.entropic_expansion.blockentity.TransceiverEntity;

public class Transceiver extends Block implements EntityBlock {
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final BooleanProperty HAS_PROVIDER = BooleanProperty.create("has_provider");
	public static final BooleanProperty HAS_RECEIVER = BooleanProperty.create("has_receiver");

	public Transceiver(Properties pProp) {
		super(pProp);
		
		registerDefaultState(stateDefinition.any()
			.setValue(FACING, Direction.NORTH)
			.setValue(POWERED, false)
			.setValue(HAS_PROVIDER, false)
			.setValue(HAS_RECEIVER, false)
		);
	}
	 
	 @Override
	 public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
		 this.updateNeighborsInFront(pLevel, pPos, pState);
	 }

	 @Override
	 public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		 if (!pIsMoving && !pState.is(pNewState.getBlock())) {
			 this.updateNeighborsInFront(pLevel, pPos, pState);
		 }
	 }

	 @Override
	 public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
		 boolean powered = pState.getValue(POWERED);
         int input = this.getInputSignal(pLevel, pPos, pState);
         boolean hasInput = input > 0 || checkProviderState(pState, pLevel, pPos);
         if (powered && !hasInput) pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(false)), 2);
         else if (!powered) {
        	 pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(true)), 2);
         }
         
         this.updateNeighborsInFront(pLevel, pPos, pState);
         this.updateTarget(pLevel, pPos, pState);
	 }
	 
	 public boolean checkProviderState(BlockState pState, Level pLevel, BlockPos pPos) {
		 BlockEntity blockentity = pLevel.getBlockEntity(pPos);
		 if(!blockentity.getBlockState().getValue(HAS_PROVIDER)) return false;
		 
		 BlockEntity provider = pLevel.getBlockEntity(((TransceiverEntity) blockentity).provider);
				 
		 if(!(provider instanceof TransceiverEntity)) return false;
			 
		 return provider.getBlockState().getValue(POWERED);
	 }
	 
	 @Override
	 public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
		 if (!pLevel.isClientSide) {
			 boolean powered = pState.getValue(POWERED);
			 int input = getInputSignal(pLevel, pPos, pState);
			 boolean hasInput = input > 0 || checkProviderState(pState, pLevel, pPos);
			 if (powered != hasInput ) {
				 if (powered && net.neoforged.neoforge.event.EventHooks.onNeighborNotify(pLevel, pPos, pLevel.getBlockState(pPos), java.util.EnumSet.of(pState.getValue(FACING).getOpposite()), false).isCanceled()) {
					 return;
				 } else {
					 pLevel.setBlock(pPos, pState.cycle(POWERED), 2);
				 }
			 }
		 }
	 }
	 
	 protected void updateNeighborsInFront(Level pLevel, BlockPos pPos, BlockState pState) {
	        Direction direction = pState.getValue(FACING);
	        BlockPos blockpos = pPos.relative(direction.getOpposite());
	        if (net.neoforged.neoforge.event.EventHooks.onNeighborNotify(pLevel, pPos, pLevel.getBlockState(pPos), java.util.EnumSet.of(direction.getOpposite()), false).isCanceled())
	            return;
	        pLevel.neighborChanged(blockpos, this, pPos);
	        pLevel.updateNeighborsAtExceptFromFacing(blockpos, this, direction);
	 }
	 
	 protected void updateTarget(Level pLevel, BlockPos pPos, BlockState pState) {
		 BlockEntity blockentity = pLevel.getBlockEntity(pPos);
		 if(blockentity instanceof TransceiverEntity) {
			 BlockPos targetPos = blockentity.getBlockPos();
			 pLevel.neighborChanged(targetPos, null, pPos);
		 }
	 }
	 
	 @Override
	 public void wasExploded(Level pLevel, BlockPos pPos, Explosion pExplosion) {
		 cleanup(pLevel,pPos);
	 }
	 
	 @Override
	 public BlockState playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
		 cleanup(pLevel,pPos);
		 return super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
	 }
	 
	 public void cleanup(Level pLevel, BlockPos pPos) {
		 BlockEntity blockentity = pLevel.getBlockEntity(pPos);
		 if(blockentity instanceof TransceiverEntity) {
			 if(((TransceiverEntity) blockentity).hasReceiver()) {
				 TransceiverEntity targetBlockEntity = (TransceiverEntity) pLevel.getBlockEntity(((TransceiverEntity) blockentity).receiver);
				 if(targetBlockEntity != null) {
					 BlockState targetState = targetBlockEntity.getBlockState().setValue(HAS_PROVIDER, false);
					 targetBlockEntity.provider = null;
					 pLevel.setBlockAndUpdate(targetBlockEntity.getBlockPos(), targetState);
				 }
			 }
			 
			 if(((TransceiverEntity) blockentity).hasProvider()) {
				 TransceiverEntity targetBlockEntity = (TransceiverEntity) pLevel.getBlockEntity(((TransceiverEntity) blockentity).provider);
				 if(targetBlockEntity != null) {
					 BlockState targetState = targetBlockEntity.getBlockState().setValue(HAS_RECEIVER, false);
					 targetBlockEntity.receiver = null;
					 pLevel.setBlockAndUpdate(targetBlockEntity.getBlockPos(), targetState);
				 }
			 }
		 }
	 }
	    
	 
	 protected int getInputSignal(Level pLevel, BlockPos pPos, BlockState pState) {
		 return getInputSignal(pLevel, pPos, pState, pState.getValue(FACING));

	 }
	 
	 protected int getInputSignal(Level pLevel, BlockPos pPos, BlockState pState, Direction direction) {
		 BlockPos blockpos = pPos.relative(direction);
		 int i = pLevel.getSignal(blockpos, direction);
		 if (i >= 15) {
			 return i;
		 } else {
			 BlockState blockstate = pLevel.getBlockState(blockpos);
			 return Math.max(i, blockstate.is(Blocks.REDSTONE_WIRE) ? blockstate.getValue(RedStoneWireBlock.POWER) : 0);
		 }
	 }


	@Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, POWERED, HAS_PROVIDER, HAS_RECEIVER);
    }
	
	@Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		Level pLevel = pContext.getLevel();
		BlockPos pPos = pContext.getClickedPos();
		BlockState pState = pLevel.getBlockState(pPos);
		Direction direction = pContext.getNearestLookingDirection();
		
        return this.defaultBlockState().setValue(FACING, direction).setValue(POWERED, getInputSignal(pLevel, pPos, pState, direction) > 0);
    }
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new TransceiverEntity(pPos, pState);
	}
	
	@Override
	public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pSide == pBlockState.getValue(FACING) ? this.getSignalForState(pBlockState) : 0;
    }
	
	protected int getSignalForState(BlockState pState) {
        return pState.getValue(POWERED) ? 15 : 0;
    }
	
	public static Boolean noOption(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return (boolean)false;
    }

}

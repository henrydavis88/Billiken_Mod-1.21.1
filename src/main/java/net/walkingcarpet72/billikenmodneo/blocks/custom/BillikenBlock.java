package net.walkingcarpet72.billikenmodneo.blocks.custom;

import net.minecraft.world.ItemInteractionResult;
import net.walkingcarpet72.billikenmodneo.Config;
import net.walkingcarpet72.billikenmodneo.blocks.ModBlocks;
import net.walkingcarpet72.billikenmodneo.entity.ModEntities;
import net.walkingcarpet72.billikenmodneo.entity.custom.BillikenEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;

public class BillikenBlock extends Block {
    public BillikenBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {

        if(pStack.is(ModBlocks.TUITION_BLOCK.get().asItem())) {
            pLevel.playSound(pPlayer, pPos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 1f, 1f);
            pStack.consume(1, pPlayer);
            spawnBilliken(pLevel, pPos);
            pLevel.removeBlock(pPos, false);
        }

        return ItemInteractionResult.SUCCESS;
    }

    private void spawnBilliken(Level pLevel, BlockPos pPos) {
        BillikenEntity billiken = ModEntities.BILLIKEN.get().create(pLevel);

        if (billiken != null) {
            billiken.moveTo((double)pPos.getX(), (double)pPos.getY() + 1, (double)pPos.getZ(), 0.0F, 0.0F);
            pLevel.addFreshEntity(billiken);
            billiken.spawnAnim();
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (Config.billikenKills) {
            player.setHealth(0);
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}

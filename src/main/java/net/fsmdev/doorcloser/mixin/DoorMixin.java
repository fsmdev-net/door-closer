package net.fsmdev.doorcloser.mixin;

import net.fsmdev.doorcloser.Door;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.tick.SimpleTickScheduler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static net.fsmdev.doorcloser.DoorCloser.doorsToClose;

@Mixin(DoorBlock.class)
public abstract class DoorMixin {

    @Shadow @Final public static BooleanProperty OPEN;
    @Unique
    Map<String, Integer> closeTimes;

    @Unique
    private boolean hasToClose;

    @Accessor
    abstract BlockSetType getBlockSetType();

    @Inject(method="<init>", at=@At("TAIL"))
    private void init(BlockSetType type, AbstractBlock.Settings settings, CallbackInfo ci) {
        closeTimes = new HashMap<>();
        closeTimes.put("block.minecraft.gold_block", 30);
        closeTimes.put("block.minecraft.iron_block", 50);
    }

    @Inject(method="onUse",at=@At("HEAD"))
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (!world.isClient()) {
            if (getBlockSetType().canOpenByHand()) {
                Block blockBelow = state.getBlock().getClass() == world.getBlockState(pos.down(1)).getBlock().getClass() ? world.getBlockState(pos.down(2)).getBlock() : world.getBlockState(pos.down(1)).getBlock();
                int closeTime = closeTimes.getOrDefault(blockBelow.getTranslationKey(), 0);
                if (closeTime != 0) {
                    if (!state.get(OPEN)) {
                        doorsToClose.put(new AbstractMap.SimpleEntry<>(pos, world), closeTime);
                    }
                }
            }
        }
    }
}

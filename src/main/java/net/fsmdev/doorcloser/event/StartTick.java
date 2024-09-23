package net.fsmdev.doorcloser.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fsmdev.doorcloser.Door;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

import static net.fsmdev.doorcloser.DoorCloser.doorsToClose;

public class StartTick implements ServerTickEvents.StartTick{
    @Override
    public void onStartTick(MinecraftServer server) {
        if (!doorsToClose.isEmpty()) {
            for (Map.Entry<BlockPos,World> door : doorsToClose.keySet()) {
                doorsToClose.put(door, doorsToClose.get(door) - 1);
                if (doorsToClose.get(door) <= 0) {
                    BlockState doorBlockState = door.getValue().getBlockState(door.getKey());
                    if (doorBlockState.getBlock() instanceof DoorBlock) {
                        if (((DoorBlock) doorBlockState.getBlock()).isOpen(doorBlockState)) {
                            ((DoorBlock) doorBlockState.getBlock()).setOpen(null, door.getValue(), doorBlockState, door.getKey(), false);
                        }
                    }
                }
            }
            doorsToClose.entrySet().removeIf(e -> e.getValue() <= 0);
        }
    }
}

package net.fsmdev.doorcloser;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class Door {

    final private World world;
    final private BlockPos pos;

    public Door(World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
    }

    public World getWorld() {
        return this.world;
    }

    public BlockPos getPos() {
        return this.pos;
    }
}

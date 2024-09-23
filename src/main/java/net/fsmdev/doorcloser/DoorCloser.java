package net.fsmdev.doorcloser;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fsmdev.doorcloser.event.StartTick;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DoorCloser implements ModInitializer {
	public static final String MOD_ID = "doorcloser";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Map<Map.Entry<BlockPos, World>,Integer> doorsToClose = new HashMap<>();

	@Override
	public void onInitialize() {
		ServerTickEvents.START_SERVER_TICK.register(new StartTick());
		LOGGER.info("Door Closer initialized!");
	}
}
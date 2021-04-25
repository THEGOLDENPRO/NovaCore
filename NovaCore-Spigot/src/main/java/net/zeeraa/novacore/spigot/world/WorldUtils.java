package net.zeeraa.novacore.spigot.world;

import java.util.UUID;

import org.bukkit.World;
import org.bukkit.entity.Entity;

public class WorldUtils {
	public static Entity getEntityByUUID(World world, UUID uuid) {
		for (Entity entity : world.getEntities()) {
			if (entity.getUniqueId().toString().equalsIgnoreCase(uuid.toString())) {
				return entity;
			}
		}

		return null;
	}
}
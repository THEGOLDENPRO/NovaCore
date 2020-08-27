package net.zeeraa.novacore.spigot.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;

/**
 * Functions used for projectiles
 * @author Zeeraa
 */
public class ProjectileUtils {
	/**
	 * Check if a a entity is a {@link Projectile}
	 * @param entity The {@link Entity} to check
	 * @return <code>true</code> if the {@link Entity} is a {@link Projectile}
	 */
	public static boolean isProjectile(Entity entity) {
		return entity instanceof Projectile;
	}

	/**
	 * Try to get the {@link Entity} that fired a {@link Projectile} {@link Entity}
	 * 
	 * @param projectile A {@link Entity} that might be a {@link Projectile}
	 * @return The entity that fired the {@link Projectile} or <code>null</code> if
	 *         there was no shooter or if the {@link Projectile} {@link Entity} is
	 *         not a {@link Projectile}
	 */
	public static Entity getProjectileShooterEntity(Entity projectile) {
		if (isProjectile(projectile)) {
			return (Entity) ((Projectile) projectile).getShooter();
		}

		return null;
	}
}
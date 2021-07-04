package net.zeeraa.novacore.spigot.module.modules.jumppad;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * The default {@link JumpPadEffect} in novacore
 * @author Zeeraa
 */
public class DefaultJumpPadEffect implements JumpPadEffect {
	@Override
	public void playJumpPadEffect(JumpPad jumpPad, Player player) {
		player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1F, 1F);
		for (int i = 0; i <= 20; i++) {
			player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, i);
		}
	}
}
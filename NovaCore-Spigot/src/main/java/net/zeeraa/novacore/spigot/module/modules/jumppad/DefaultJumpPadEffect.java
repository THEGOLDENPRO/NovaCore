package net.zeeraa.novacore.spigot.module.modules.jumppad;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class DefaultJumpPadEffect implements JumpPadEffect {
	@Override
	public void playJumpPadEffect(JumpPad jumpPad, Player player) {
		jumpPad.getLocation().getWorld().playSound(jumpPad.getLocation(), Sound.GHAST_FIREBALL, 1F, 1F);
		for (int i = 0; i <= 20; i++) {
			player.getWorld().playEffect(jumpPad.getLocation(), Effect.SMOKE, i);
		}
	}
}
package net.zeeraa.novacore.spigot.module.modules.jumppad;

import org.bukkit.entity.Player;

/**
 * This is used to create custom jum pad effects
 * @author Zeeraa
 */
public interface JumpPadEffect {
	public void playJumpPadEffect(JumpPad jumpPad, Player player);
}
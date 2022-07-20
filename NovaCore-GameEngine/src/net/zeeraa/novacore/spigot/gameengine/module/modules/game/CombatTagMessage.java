package net.zeeraa.novacore.spigot.gameengine.module.modules.game;

import org.bukkit.entity.Player;

public interface CombatTagMessage {
	public void showTaggedMessage(Player player);

	public void showNoLongerTaggedMessage(Player player);
}
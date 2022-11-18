package net.zeeraa.novacore.spigot.gameengine.module.modules.game;

import org.bukkit.entity.Player;

public interface CombatTagMessage {
	void showTaggedMessage(Player player);

	void showNoLongerTaggedMessage(Player player);
}
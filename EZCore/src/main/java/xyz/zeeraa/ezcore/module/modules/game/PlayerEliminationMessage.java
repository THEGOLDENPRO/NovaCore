package xyz.zeeraa.ezcore.module.modules.game;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;

public interface PlayerEliminationMessage {
	/**
	 * Show the player elimination message
	 * 
	 * @param player The player that was eliminated
	 * @param killer The entity that killed the player. this will always be
	 *               <code>null</code> unless the {@link PlayerEliminationReason} is
	 *               {@link PlayerEliminationReason#KILLED}
	 * @param reason The {@link PlayerEliminationReason}
	 */
	public abstract void showPlayerEliminatedMessage(OfflinePlayer player, Entity killer, PlayerEliminationReason reason);
}
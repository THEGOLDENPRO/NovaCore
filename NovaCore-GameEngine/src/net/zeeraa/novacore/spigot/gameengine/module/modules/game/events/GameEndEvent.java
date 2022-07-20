package net.zeeraa.novacore.spigot.gameengine.module.modules.game.events;

import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.GameEndReason;

/**
 * Called when a game is starting
 * 
 * @author Zeeraa
 */
public class GameEndEvent extends GameEvent {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private GameEndReason reason;

	public GameEndEvent(Game game, GameEndReason reason) {
		super(game);
		this.reason = reason;
	}

	/**
	 * Get the reason why the game ended
	 * 
	 * @return {@link GameEndReason}
	 */
	public GameEndReason getReason() {
		return reason;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}
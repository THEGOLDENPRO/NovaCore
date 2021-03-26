package net.zeeraa.novacore.spigot.module.modules.game.events;

import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.module.modules.game.Game;

/**
 * Called when a game has started and the count down has finished
 * <p>
 * Unlike {@link GameStartEvent} this has to be called in the game code. See
 * {@link Game} for more info about how to do this
 * 
 * @author Zeeraa
 */
public class GameBeginEvent extends GameEvent {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	public GameBeginEvent(Game game) {
		super(game);
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}
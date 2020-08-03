package xyz.zeeraa.novacore.module.modules.game.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import xyz.zeeraa.novacore.module.modules.game.PlayerEliminationReason;

public class PlayerEliminatedEvent extends Event implements Cancellable {
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	private OfflinePlayer player;
	private Entity killer;
	private PlayerEliminationReason reason;
	
	private boolean cancel;

	public PlayerEliminatedEvent(OfflinePlayer player, Entity killer, PlayerEliminationReason reason) {
		this.player = player;
		this.killer = killer;
		this.reason = reason;

		this.cancel = false;
	}

	/**
	 * Get the player that was eliminated
	 * @return {@link OfflinePlayer}
	 */
	public OfflinePlayer getPlayer() {
		return player;
	}

	/**
	 * Get the entity that killed the player. this will always be <code>null</code>
	 * unless the {@link PlayerEliminationReason} is
	 * {@link PlayerEliminationReason#KILLED}
	 * 
	 * @return {@link Entity}
	 */
	public Entity getKiller() {
		return killer;
	}

	/**
	 * Get the reason why the player was eliminated
	 * 
	 * @return {@link PlayerEliminationReason}
	 */
	public PlayerEliminationReason getReason() {
		return reason;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;

	}

	@Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
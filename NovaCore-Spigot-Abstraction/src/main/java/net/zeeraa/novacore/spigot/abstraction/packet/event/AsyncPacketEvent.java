package net.zeeraa.novacore.spigot.abstraction.packet.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AsyncPacketEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();

	protected Player who;

	private boolean cancelled;

	public Player getPlayer() {
		return who;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public AsyncPacketEvent(Player player) {
		super(true);
		who = player;
	}
}
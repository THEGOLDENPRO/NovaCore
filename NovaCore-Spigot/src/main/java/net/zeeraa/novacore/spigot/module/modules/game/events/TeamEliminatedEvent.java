package net.zeeraa.novacore.spigot.module.modules.game.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.teams.Team;

/**
 * Called when a team is eliminated
 * 
 * @author Zeeraa
 */
public class TeamEliminatedEvent extends Event {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private Team team;
	private int placement;

	public TeamEliminatedEvent(Team team, int placement) {
		this.team = team;
		this.placement = placement;
	}

	/**
	 * Get the {@link Team} that was eliminated
	 * 
	 * @return The eliminated {@link Team}
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Get the placement of the eliminated team
	 * 
	 * @return Placement number
	 */
	public int getPlacement() {
		return placement;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}
package xyz.zeeraa.ezcore.teams;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

public abstract class TeamManager {
	private List<Team> teams;

	public TeamManager() {
		this.teams = new ArrayList<Team>();
	}

	public List<Team> getTeams() {
		return teams;
	}

	public Team getPlayerTeam(OfflinePlayer player) {
		return this.getPlayerTeam(player.getUniqueId());
	}

	public Team getPlayerTeam(UUID uuid) {
		for (Team team : teams) {
			if (team.isMember(uuid)) {
				return team;
			}
		}
		return null;
	}

	public boolean isInSameTeam(OfflinePlayer player1, OfflinePlayer player2) {
		return this.isInSameTeam(player1.getUniqueId(), player2.getUniqueId());
	}

	public boolean isInSameTeam(UUID uuid1, UUID uuid2) {
		Team team1 = this.getPlayerTeam(uuid1);

		if (team1 != null) {
			return team1.isMember(uuid2);
		}

		return false;
	}
}
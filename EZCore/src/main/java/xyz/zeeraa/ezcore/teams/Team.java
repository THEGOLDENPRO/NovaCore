package xyz.zeeraa.ezcore.teams;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

public abstract class Team {
	private UUID teamUuid;
	private List<UUID> members;

	public Team() {
		this.teamUuid = UUID.randomUUID();
		members = new ArrayList<UUID>();
	}

	public List<UUID> getMembers() {
		return members;
	}

	public boolean isMember(OfflinePlayer player) {
		return isMember(player.getUniqueId());
	}

	public boolean isMember(UUID uuid) {
		return members.contains(uuid);
	}

	public UUID getTeamUuid() {
		return teamUuid;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Team) {
			return this.getTeamUuid().equals(((Team) obj).getTeamUuid());
		}

		return false;
	}
	
	public abstract ChatColor getTeamColor();
}
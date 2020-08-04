package xyz.zeeraa.novacore.teams;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class Team {
	protected UUID teamUuid;
	protected List<UUID> members;

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

	/**
	 * Send a message to all team members on this servers
	 * 
	 * @param message The message to send
	 * @return number of players that the message was sent to
	 */
	public int sendMessage(String message) {
		int count = 0;

		for (UUID uuid : members) {
			Player player = Bukkit.getServer().getPlayer(uuid);
			if (player != null) {
				if (player.isOnline()) {
					player.sendMessage(message);
					count++;
				}
			}
		}

		return count;
	}

	public abstract ChatColor getTeamColor();

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Team) {
			return this.getTeamUuid().equals(((Team) obj).getTeamUuid());
		}

		return false;
	}
}
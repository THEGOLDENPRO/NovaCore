package xyz.zeeraa.ezcore.module.game.teams;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Represents a team of players in a game
 * 
 * @author Zeeraa
 */
public class Team {
	private int number;
	private ChatColor color;
	private String name;
	private List<UUID> members;

	public Team(int number, ChatColor color, String name, List<UUID> members) {
		this.number = number;
		this.color = color;
		this.name = name;
		this.members = members;
	}

	/**
	 * Get the team number
	 * 
	 * @return Team number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Get the color to use when displaying this teams name
	 * 
	 * @return {@link ChatColor} of this team
	 */
	public ChatColor getColor() {
		return color;
	}

	/**
	 * Get the display name of this team
	 * 
	 * @return Display name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get a {@link List} containing the {@link UUID} of all the members in this
	 * team
	 * 
	 * @return {@link List} with members
	 */
	public List<UUID> getMembers() {
		return members;
	}

	/**
	 * Add a player to the team
	 * 
	 * @param player The {@link OfflinePlayer} to add
	 * @return <code>false</code> if the player was already added
	 */
	public boolean addMember(OfflinePlayer player) {
		return addMember(player.getUniqueId());
	}

	/**
	 * Add a player to the team
	 * 
	 * @param uuid The UUID of the player to add
	 * @return <code>false</code> if the player was already added
	 */
	public boolean addMember(UUID uuid) {
		if (members.contains(uuid)) {
			return false;
		}
		members.add(uuid);
		return true;
	}

	/**
	 * Add a player to the team
	 * 
	 * @param player The {@link OfflinePlayer} to add
	 * @return <code>false</code> if the player was already added
	 */
	public boolean removeMember(OfflinePlayer player) {
		return this.removeMember(player.getUniqueId());
	}

	/**
	 * Add a player to the team
	 * 
	 * @param uuid The UUID of the player to add
	 * @return <code>false</code> if the player was already added
	 */
	public boolean removeMember(UUID uuid) {
		if (members.contains(uuid)) {
			return false;
		}
		members.add(uuid);
		return true;
	}

	/**
	 * Send a message to all the online team members in this team
	 * 
	 * @param message The message to send
	 * @return The number of players that the message was sent to
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
}
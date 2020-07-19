package xyz.zeeraa.ezcore.module.scoreboard;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

import me.johnnykpl.scoreboardwrapper.ScoreboardWrapper;
import xyz.zeeraa.ezcore.module.EZModule;

public class EZSimpleScoreboard extends EZModule implements Listener {
	private HashMap<UUID, ScoreboardWrapper> scoreboards;

	private String deafultScoreboardTitle;
	private boolean createOnJoin;

	private static EZSimpleScoreboard instance;

	public static EZSimpleScoreboard getInstance() {
		return instance;
	}

	@Override
	public void onLoad() {
		instance = this;
		this.deafultScoreboardTitle = "";
		this.createOnJoin = true;
		this.scoreboards = new HashMap<UUID, ScoreboardWrapper>();
	}

	@Override
	public void onDisable() {
		scoreboards.clear();
	}

	@Override
	public String getName() {
		return "EZSimpleScoreboard";
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (createOnJoin) {
			Player p = e.getPlayer();

			if (!hasScoreboardWrapper(p)) {
				createScoreboard(p);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		if (hasScoreboardWrapper(p)) {
			scoreboards.remove(p.getUniqueId());
		}
	}

	public String getDeafultScoreboardTitle() {
		return deafultScoreboardTitle;
	}

	public void setDeafultScoreboardTitle(String deafultScoreboardTitle) {
		this.deafultScoreboardTitle = deafultScoreboardTitle;
	}

	public boolean isCreateOnJoin() {
		return createOnJoin;
	}

	public void setCreateOnJoin(boolean createOnJoin) {
		this.createOnJoin = createOnJoin;
	}

	public HashMap<UUID, ScoreboardWrapper> getScoreboards() {
		return scoreboards;
	}

	public boolean hasScoreboardWrapper(OfflinePlayer player) {
		return hasScoreboardWrapper(player.getUniqueId());
	}

	public boolean hasScoreboardWrapper(UUID uuid) {
		return scoreboards.containsKey(uuid);
	}

	public ScoreboardWrapper createScoreboard(Player player) {
		return createScoreboard(player, deafultScoreboardTitle);
	}

	public ScoreboardWrapper createScoreboard(Player player, String title) {
		if (scoreboards.containsKey(player.getUniqueId())) {
			return scoreboards.get(player.getUniqueId());
		}

		ScoreboardWrapper scoreboardWrapper = new ScoreboardWrapper(title);
		player.setScoreboard(scoreboardWrapper.getScoreboard());
		scoreboards.put(player.getUniqueId(), scoreboardWrapper);

		return scoreboardWrapper;
	}

	public ScoreboardWrapper getScoreboardWrapper(OfflinePlayer player) {
		return getScoreboardWrapper(player.getUniqueId());
	}

	public ScoreboardWrapper getScoreboardWrapper(UUID uuid) {
		return scoreboards.get(uuid);
	}

	public Scoreboard getScoreboard(OfflinePlayer player) {
		return getScoreboard(player.getUniqueId());
	}

	public Scoreboard getScoreboard(UUID uuid) {
		if (hasScoreboardWrapper(uuid)) {
			return scoreboards.get(uuid).getScoreboard();
		}
		return null;
	}
}
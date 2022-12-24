package net.zeeraa.novacore.spigot.gamemapdesigntoolkit;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.zeeraa.novacore.spigot.command.CommandRegistry;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command.CopyItemBase64;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command.CopyLocationCommand;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command.CopyVectorAreaCommand;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command.NovaMapWandCommand;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command.createblockreplacercache.CreateBlockReplacerCacheCommand;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.items.NovaAreaSelectionWand;
import net.zeeraa.novacore.spigot.module.ModuleManager;
import net.zeeraa.novacore.spigot.module.modules.customitems.CustomItemManager;

public class GameMapDesignToolkit extends JavaPlugin implements Listener {
	private static GameMapDesignToolkit instance;

	private Map<Player, PlayerAreaSelection> selections;

	public static GameMapDesignToolkit getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		GameMapDesignToolkit.instance = this;

		getDataFolder().mkdir();

		selections = new HashMap<>();

		ModuleManager.require(CustomItemManager.class);

		try {
			CustomItemManager.getInstance().addCustomItem(NovaAreaSelectionWand.class);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		Bukkit.getServer().getPluginManager().registerEvents(this, this);

		CommandRegistry.registerCommand(new CopyLocationCommand(this));
		CommandRegistry.registerCommand(new CopyItemBase64(this));
		CommandRegistry.registerCommand(new CreateBlockReplacerCacheCommand(this));
		CommandRegistry.registerCommand(new CopyVectorAreaCommand(this));
		CommandRegistry.registerCommand(new NovaMapWandCommand(this));
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll((Plugin) this);
	}

	public PlayerAreaSelection getPlayerSelection(Player player) {
		if (selections.containsKey(player)) {
			return selections.get(player);
		}

		Location defaultLocation = new Location(player.getWorld(), 0D, 0D, 0D);
		PlayerAreaSelection selection = new PlayerAreaSelection(defaultLocation.clone(), defaultLocation.clone());

		if (isEnabled()) {
			selections.put(player, selection);
		}

		return selection;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (selections.containsKey(player)) {
			selections.remove(player);
		}
	}
}
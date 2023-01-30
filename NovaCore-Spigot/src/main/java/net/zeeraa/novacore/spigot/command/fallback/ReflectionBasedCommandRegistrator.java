package net.zeeraa.novacore.spigot.command.fallback;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.abstraction.CommandRegistrator;

public class ReflectionBasedCommandRegistrator implements CommandRegistrator {
	@Override
	public void registerCommand(Plugin plugin, Command command) {
		this.getCommandMap().register(plugin.getName().toLowerCase(), command);
	}

	@Override
	public CommandMap getCommandMap() {
		try {
			String version = NovaCore.getInstance().getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
			String name = "org.bukkit.craftbukkit." + version + ".CraftServer";
			Class<?> craftserver;
			craftserver = Class.forName(name);
			return (SimpleCommandMap) craftserver.cast(NovaCore.getInstance().getServer()).getClass().getMethod("getCommandMap").invoke(NovaCore.getInstance().getServer());
		} catch (Exception e) {
			Log.error("ReflectionBasedCommandRegistrator", "getCommandMap() failed. " + e.getClass().getName() + " " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean syncCommands() {
		return false;
	}

	@Override
	public Map<String, Command> tryGetKnownCommandsFromSimpleCommandMap(SimpleCommandMap commandMap) {
		// Since this command registrator is used when we have no idea what version we
		// are running we return null and let NovaCore try to use reflection instead
		return null;
	}
}
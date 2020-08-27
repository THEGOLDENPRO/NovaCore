package net.zeeraa.novacore.spigot.version.v1_8_R3;

import net.zeeraa.novacore.spigot.abstraction.ActionBar;
import net.zeeraa.novacore.spigot.abstraction.CommandRegistrator;
import net.zeeraa.novacore.spigot.abstraction.Listeners;

public class VersionIndependantLoader implements net.zeeraa.novacore.spigot.abstraction.VersionIndependantLoader {
	@Override
	public CommandRegistrator getCommandRegistrator() {
		return new net.zeeraa.novacore.spigot.version.v1_8_R3.CommandRegistrator();
	}

	@Override
	public ActionBar getActionBar() {
		return new net.zeeraa.novacore.spigot.version.v1_8_R3.ActionBar();
	}

	@Override
	public VersionIndependentUtils getVersionIndependentUtils() {
		return new net.zeeraa.novacore.spigot.version.v1_8_R3.VersionIndependentUtils();
	}

	@Override
	public Listeners getListeners() {
		return new net.zeeraa.novacore.spigot.version.v1_8_R3.Listeners();
	}
}
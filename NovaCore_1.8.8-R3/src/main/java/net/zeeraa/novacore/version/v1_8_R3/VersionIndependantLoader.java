package net.zeeraa.novacore.version.v1_8_R3;

import net.zeeraa.novacore.abstraction.ActionBar;
import net.zeeraa.novacore.abstraction.CommandRegistrator;
import net.zeeraa.novacore.abstraction.Listeners;

public class VersionIndependantLoader implements net.zeeraa.novacore.abstraction.VersionIndependantLoader {
	@Override
	public CommandRegistrator getCommandRegistrator() {
		return new net.zeeraa.novacore.version.v1_8_R3.CommandRegistrator();
	}

	@Override
	public ActionBar getActionBar() {
		return new net.zeeraa.novacore.version.v1_8_R3.ActionBar();
	}

	@Override
	public VersionIndependentUtils getVersionIndependentUtils() {
		return new net.zeeraa.novacore.version.v1_8_R3.VersionIndependentUtils();
	}

	@Override
	public Listeners getListeners() {
		return new net.zeeraa.novacore.version.v1_8_R3.Listeners();
	}
}
package xyz.zeeraa.novacore.version.v1_8_R3;

import xyz.zeeraa.novacore.abstraction.ActionBar;
import xyz.zeeraa.novacore.abstraction.CommandRegistrator;

public class VersionIndependantLoader implements xyz.zeeraa.novacore.abstraction.VersionIndependantLoader {
	@Override
	public CommandRegistrator getCommandRegistrator() {
		return new xyz.zeeraa.novacore.version.v1_8_R3.CommandRegistrator();
	}

	@Override
	public ActionBar getActionBar() {
		return new xyz.zeeraa.novacore.version.v1_8_R3.ActionBar();
	}

	@Override
	public VersionIndependentUtils getVersionIndependentUtils() {
		return new xyz.zeeraa.novacore.version.v1_8_R3.VersionIndependentUtils();
	}
}
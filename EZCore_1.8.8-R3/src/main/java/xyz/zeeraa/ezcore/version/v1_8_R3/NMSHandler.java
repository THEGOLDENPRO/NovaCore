package xyz.zeeraa.ezcore.version.v1_8_R3;

import xyz.zeeraa.ezcore.abstraction.ActionBar;
import xyz.zeeraa.ezcore.abstraction.CommandRegistrator;
import xyz.zeeraa.ezcore.abstraction.VersionIndependentUtils;

public class NMSHandler implements xyz.zeeraa.ezcore.abstraction.NMSHandler {
	@Override
	public CommandRegistrator getCommandRegistrator() {
		return new xyz.zeeraa.ezcore.version.v1_8_R3.CommandRegistrator();
	}

	@Override
	public ActionBar getActionBar() {
		return new xyz.zeeraa.ezcore.version.v1_8_R3.ActionBar();
	}

	@Override
	public VersionIndependentUtils getVersionIndependentUtils() {
		return new xyz.zeeraa.ezcore.version.v1_8_R3.VersionIndependentUtils();
	}
}
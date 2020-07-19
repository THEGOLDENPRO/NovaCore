package xyz.zeeraa.ezcore.version.v1_8_R3;

import xyz.zeeraa.ezcore.abstraction.CommandRegistrator;

public class NMSHandler implements xyz.zeeraa.ezcore.abstraction.NMSHandler {

	@Override
	public CommandRegistrator getCommandRegistrator() {
		return new xyz.zeeraa.ezcore.version.v1_8_R3.CommandRegistrator();
	}
}
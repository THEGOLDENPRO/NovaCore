package net.zeeraa.novacore.spigot.abstraction;

public interface VersionIndependantLoader {
	public CommandRegistrator getCommandRegistrator();

	public VersionIndependentUtils getVersionIndependentUtils();

	public Listeners getListeners();
}
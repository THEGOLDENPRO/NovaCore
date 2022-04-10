package net.zeeraa.novacore.spigot.abstraction;

public interface VersionIndependantLoader {
	public CommandRegistrator getCommandRegistrator();

	public VersionIndependantUtils getVersionIndependentUtils();

	public Listeners getListeners();
}
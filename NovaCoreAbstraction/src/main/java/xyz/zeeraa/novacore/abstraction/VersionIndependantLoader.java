package xyz.zeeraa.novacore.abstraction;

public interface VersionIndependantLoader {
	public CommandRegistrator getCommandRegistrator();
	
	public ActionBar getActionBar();
	
	public VersionIndependantUtils getVersionIndependentUtils();
}
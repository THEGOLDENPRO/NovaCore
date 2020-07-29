package xyz.zeeraa.ezcore.abstraction;

public interface NMSHandler {
	public CommandRegistrator getCommandRegistrator();
	
	public ActionBar getActionBar();
	
	public VersionIndependentUtils getVersionIndependentUtils();
}
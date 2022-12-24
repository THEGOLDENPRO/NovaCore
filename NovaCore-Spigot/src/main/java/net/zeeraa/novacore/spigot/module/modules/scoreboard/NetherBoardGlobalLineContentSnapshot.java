package net.zeeraa.novacore.spigot.module.modules.scoreboard;

import java.util.Map;

public class NetherBoardGlobalLineContentSnapshot {
	private Map<Integer, String> globalLines;
	
	public NetherBoardGlobalLineContentSnapshot(Map<Integer, String> globalLines) {
		this.globalLines = globalLines;
	}
	
	public Map<Integer, String> getGlobalLines() {
		return globalLines;
	}
	
	public void restore() {
		NetherBoardScoreboard.getInstance().restoreGlobalLineSnapshot(this);
	}
}
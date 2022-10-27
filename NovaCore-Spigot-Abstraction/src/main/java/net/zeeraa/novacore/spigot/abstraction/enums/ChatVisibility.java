package net.zeeraa.novacore.spigot.abstraction.enums;

public enum ChatVisibility {
	FULL("a"), SYSTEM("b"), HIDDEN("c");
	
	private final String nmsVal;
	
	
	ChatVisibility(String nmsVal) {
		this.nmsVal = nmsVal;
	}
	
	public String getNmsVal() {
		return nmsVal;
	}
	
	public static ChatVisibility fromNMS(String nmsVal) {
		ChatVisibility cv = null;
		for (ChatVisibility chatVisibility : ChatVisibility.values()) {
			if (chatVisibility.getNmsVal().equals(nmsVal)) {
				cv = chatVisibility;
				break;
			}
		}
		return cv;
	}


}
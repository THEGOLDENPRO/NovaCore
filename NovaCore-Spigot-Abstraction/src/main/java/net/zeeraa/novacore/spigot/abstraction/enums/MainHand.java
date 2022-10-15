package net.zeeraa.novacore.spigot.abstraction.enums;

public enum MainHand {
	LEFT("a"), RIGHT("b");

	public String nmsValue;

	MainHand(String s) {
		this.nmsValue = s;
	}

	public static MainHand valueOfNMS(String s) {
		for (MainHand mainHand : MainHand.values()) {
			if (mainHand.nmsValue.equals(s)) {
				return mainHand;
			}
		}
		return null;
	}
}
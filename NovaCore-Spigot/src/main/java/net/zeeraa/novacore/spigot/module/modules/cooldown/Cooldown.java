package net.zeeraa.novacore.spigot.module.modules.cooldown;

public class Cooldown {
	private String name;
	private int ticksLeft;

	public Cooldown(String name, int ticksLeft) {
		this.name = name;
		this.ticksLeft = ticksLeft;
	}

	public String getName() {
		return name;
	}

	public void setTicksLeft(int ticksLeft) {
		this.ticksLeft = ticksLeft;
	}

	public int getTicksLeft() {
		return ticksLeft;
	}

	public boolean isOver() {
		return ticksLeft <= 0;
	}

	public void tick() {
		ticksLeft--;
	}
}
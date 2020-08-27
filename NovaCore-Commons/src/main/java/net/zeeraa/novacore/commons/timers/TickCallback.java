package net.zeeraa.novacore.commons.timers;

/**
 * A callback that is called every time a timer counts down
 * 
 * @author Zeeraa
 */
public interface TickCallback {
	/**
	 * 'Called when a timer counts down
	 * 
	 * @param timeLeft Ticks left, Note that the duration of a timer tick is defined
	 *                 by the timer.
	 */
	public void execute(int timeLeft);
}
package xyz.zeeraa.novacore.timers;

/**
 * Contains the basic functions in a timer
 * 
 * @author Zeeraa
 */
public interface Timer {
	/**
	 * Start the timer
	 * 
	 * @return <code>true</code> if the timer was started. <code>false</code> if it
	 *         has already been started
	 */
	public boolean start();

	/**
	 * Cancel the timer
	 * 
	 * @return <code>true</code> if the timer is canceled. <code>false</code> if it
	 *         has already been canceled
	 */
	public boolean cancel();

	/**
	 * Check if the timer has started. This does not get reset on cancel or stop
	 * 
	 * @return <code>true</code> if the timer has started
	 */
	public boolean hasStarted();

	/**
	 * Check if the timer is running
	 * 
	 * @return <code>true</code> if the timer is running. <code>false</code> if the
	 *         timer is finished or has not started
	 */
	public boolean isRunning();

	/**
	 * Get time left
	 * 
	 * @return the time left
	 */
	public int getTimeLeft();
}
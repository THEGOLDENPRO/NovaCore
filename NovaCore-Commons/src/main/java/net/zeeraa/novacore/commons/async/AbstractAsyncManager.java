package net.zeeraa.novacore.commons.async;

public interface AbstractAsyncManager {
	/**
	 * Run a runnable asynchronous
	 * 
	 * @param runnable The {@link Runnable}
	 * @param delay    The delay in ticks. For BungeeCord this will be
	 *                 <code>(delay * 50)</code> milliseconds
	 */
	void runAsync(Runnable runnable, long delay);
	
	/**
	 * Run a runnable in the main thread
	 * 
	 * @param runnable The {@link Runnable}
	 * @param delay    The delay in ticks. For BungeeCord this will be
	 *                 <code>(delay * 50)</code> milliseconds
	 */
	void runSync(Runnable runnable, long delay);
}
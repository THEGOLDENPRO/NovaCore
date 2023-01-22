package net.zeeraa.novacore.commons.utils.network.api.ip;

public interface IAsyncIPCallback {
	/**
	 * Called when the ip has been fetched or if something goes wrong
	 * 
	 * @param ip        The ip or <code>null</code> on error
	 * @param exception The {@link Exception} thrown or null on success
	 */
	void onResult(String ip, Exception exception);
}
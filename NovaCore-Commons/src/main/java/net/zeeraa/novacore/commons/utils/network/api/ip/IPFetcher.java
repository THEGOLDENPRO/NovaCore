package net.zeeraa.novacore.commons.utils.network.api.ip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import net.zeeraa.novacore.commons.async.AsyncManager;

public class IPFetcher {
	public static int fetchTimeout = 10 * 1000;

	public static final String getIPv4Sync() throws IOException {
		URL url = new URL("https://ipv4.myip.wtf/json");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("accept", "application/json");
		connection.setRequestProperty("User-Agent", "NovaCore");

		connection.setConnectTimeout(fetchTimeout);
		connection.setReadTimeout(fetchTimeout);

		connection.connect();

		InputStream responseStream = connection.getInputStream();

		InputStreamReader isr = new InputStreamReader(responseStream);
		BufferedReader rd = new BufferedReader(isr);
		StringBuilder response = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		isr.close();
		responseStream.close();
		connection.disconnect();

		JSONObject responseJson = new JSONObject(response.toString());

		return responseJson.getString("YourFuckingIPAddress");
	}

	public static final String getIPSync() throws IOException {
		URL url = new URL("https://myip.wtf/json");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("accept", "application/json");
		connection.setRequestProperty("User-Agent", "NovaCore");

		connection.setConnectTimeout(fetchTimeout);
		connection.setReadTimeout(fetchTimeout);

		connection.connect();

		InputStream responseStream = connection.getInputStream();

		InputStreamReader isr = new InputStreamReader(responseStream);
		BufferedReader rd = new BufferedReader(isr);
		StringBuilder response = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		isr.close();
		responseStream.close();
		connection.disconnect();

		JSONObject responseJson = new JSONObject(response.toString());

		return responseJson.getString("YourFuckingIPAddress");
	}

	public static final void getIPv4Async(IAsyncIPCallback callback) {
		AsyncManager.runAsync(new Runnable() {
			@Override
			public void run() {
				Exception exception = null;
				String ip = null;
				try {
					ip = IPFetcher.getIPv4Sync();
					exception = null;
				} catch (Exception e) {
					ip = null;
					exception = e;
				}

				final String finalIp = ip;
				final Exception finalException = exception;

				AsyncManager.runSync(new Runnable() {
					@Override
					public void run() {
						callback.onResult(finalIp, finalException);
					}
				});
			}
		});
	}

	public static final void getIPAsync(IAsyncIPCallback callback) {
		AsyncManager.runAsync(new Runnable() {
			@Override
			public void run() {
				Exception exception = null;
				String ip = null;
				try {
					ip = IPFetcher.getIPSync();
					exception = null;
				} catch (Exception e) {
					ip = null;
					exception = e;
				}

				final String finalIp = ip;
				final Exception finalException = exception;

				AsyncManager.runSync(new Runnable() {
					@Override
					public void run() {
						callback.onResult(finalIp, finalException);
					}
				});
			}
		});
	}
}
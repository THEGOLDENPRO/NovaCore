package net.zeeraa.novacore.commons.api.novauniverse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.api.novauniverse.callbach.IAsyncNameToUUIDCallback;
import net.zeeraa.novacore.commons.async.AsyncManager;

/**
 * 
 * @author Zeeraa
 * @since 2.0.0
 */
public class NovaUniverseAPI {
	/**
	 * Get the {@link UUID} of a player by their user name
	 * 
	 * @param name The user name of the player
	 * @return {@link UUID} of the player or <code>null</code> if not found
	 * @throws IllegalArgumentException If the user name is not alphanumeric, is
	 *                                  shorter than 3 characters or longer than 16
	 *                                  characters
	 * @throws MalformedURLException    should not be thrown unless input validation
	 *                                  fails
	 * @throws IOException              If something goes wrong while requesting the
	 *                                  data
	 */
	public static final UUID nameToUUID(String name) throws MalformedURLException, IOException {
		if (name.length() < 3 || name.length() > 16) {
			throw new IllegalArgumentException(name.length() < 3 ? "Name has to bee more than 3 characters long" : "Name cant be more than 16 characters long");
		}

		if (!StringUtils.isAlphanumeric(name)) {
			throw new IllegalArgumentException("Username contains invalid characters");
		}

		URL url = new URL("https://novauniverse.net/api/private/mojang/name_to_uuid/" + name);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("accept", "application/json");
		connection.setRequestProperty("User-Agent", "NovaCore");
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

		if (responseJson.isNull("data")) {
			return null;
		}

		return UUID.fromString(responseJson.getJSONObject("data").getString("full_uuid"));
	}

	/**
	 * Get the {@link UUID} of a player by their user name
	 * 
	 * @param name     The user name of the player
	 * @param callback The {@link IAsyncNameToUUIDCallback} to call when done
	 * @throws IllegalArgumentException If the user name is not alphanumeric, is
	 *                                  shorter than 3 characters or longer than 16
	 *                                  characters
	 * @since 2.0.0
	 */
	public static final void nameToUUIDAsync(String name, IAsyncNameToUUIDCallback callback) {
		if (name.length() < 3 || name.length() > 16) {
			throw new IllegalArgumentException(name.length() < 3 ? "Name has to bee more than 3 characters long" : "Name cant be more than 16 characters long");
		}

		if (!StringUtils.isAlphanumeric(name)) {
			throw new IllegalArgumentException("Username contains invalid characters");
		}

		AsyncManager.runAsync(new Runnable() {
			@Override
			public void run() {
				Exception exception = null;
				UUID uuid = null;
				try {
					uuid = NovaUniverseAPI.nameToUUID(name);
					exception = null;
				} catch (Exception e) {
					uuid = null;
					exception = e;
				}

				final UUID finalUUID = uuid;
				final Exception finalException = exception;

				AsyncManager.runSync(new Runnable() {
					@Override
					public void run() {
						callback.onResult(finalUUID, finalException);
					}
				});
			}
		});
	}
}
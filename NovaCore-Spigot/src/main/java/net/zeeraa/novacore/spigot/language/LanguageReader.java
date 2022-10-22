package net.zeeraa.novacore.spigot.language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class LanguageReader {
	public static void readFromJar(Class<?> clazz, String name) throws IOException {
		InputStream in = clazz.getResourceAsStream(name);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

		String line;
		StringBuilder lines = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			lines.append(line);
		}

		JSONObject json = new JSONObject(lines.toString());

		LanguageReader.read(json);
	}

	public static void read(JSONObject json) {
		String code = json.getString("language_code").toLowerCase();

		Language language = LanguageManager.getLanguage(code);

		if (language == null) {
			String displayName = json.getString("language_name");

			language = new Language(code, displayName);
		}

		JSONObject content = json.getJSONObject("content");

		for (String key : content.keySet()) {
			String value = content.getString(key);

			language.getContent().put(key, value);
		}

		LanguageManager.getLanguages().put(code, language);
	}
}
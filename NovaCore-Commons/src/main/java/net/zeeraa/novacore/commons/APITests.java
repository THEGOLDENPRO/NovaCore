package net.zeeraa.novacore.commons;

import java.util.UUID;

import net.zeeraa.novacore.commons.api.novauniverse.NovaUniverseAPI;

public class APITests {

	public static void main(String[] args) {
		try {
			System.out.println(NovaUniverseAPI.nameToUUID("Zeeraa01"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(NovaUniverseAPI.nameToUUID("Zeeraa01AAA"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(NovaUniverseAPI.getProfile(UUID.fromString("22a9eca8-2221-4bc9-b463-de0f3a0cc652")).toJSON().toString(4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			// Fake uuid
			System.out.println(NovaUniverseAPI.getProfile(UUID.fromString("22a9eca8-2221-4bc9-b463-de0f3a0cc65a")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
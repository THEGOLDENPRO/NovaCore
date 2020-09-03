package net.zeeraa.novacore.bungeecord.abstraction;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.zeeraa.novacore.commons.log.AbstractConsoleSender;

public class AbstractBungeecordConsoleSender implements AbstractConsoleSender {

	@Override
	public void sendMessage(String message) {
		ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(message));
	}

	@Override
	public void sendMessage(String[] message) {
		for (int i = 0; i < message.length; i++) {
			ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(message[i]));
		}
	}
}
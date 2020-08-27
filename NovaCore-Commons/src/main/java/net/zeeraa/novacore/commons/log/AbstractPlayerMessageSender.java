package net.zeeraa.novacore.commons.log;

import java.util.UUID;

public interface AbstractPlayerMessageSender {
	public boolean trySendMessage(UUID uuid, String message);
}
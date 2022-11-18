package net.zeeraa.novacore.commons.log;

import java.util.UUID;

public interface AbstractPlayerMessageSender {
	boolean trySendMessage(UUID uuid, String message);
}
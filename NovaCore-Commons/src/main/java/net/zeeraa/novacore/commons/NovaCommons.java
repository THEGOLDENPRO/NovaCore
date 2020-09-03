package net.zeeraa.novacore.commons;

import net.zeeraa.novacore.commons.async.AbstractAsyncManager;
import net.zeeraa.novacore.commons.log.AbstractConsoleSender;
import net.zeeraa.novacore.commons.log.AbstractPlayerMessageSender;
import net.zeeraa.novacore.commons.tasks.AbstractSimpleTaskCreator;

public class NovaCommons {
	private static AbstractPlayerMessageSender abstractPlayerMessageSender;
	private static AbstractConsoleSender abstractConsoleSender;
	private static AbstractSimpleTaskCreator abstractSimpleTaskCreator;
	private static AbstractAsyncManager abstractAsyncManager;
	private static ServerType serverType;
	
	public static AbstractPlayerMessageSender getAbstractPlayerMessageSender() {
		return abstractPlayerMessageSender;
	}
	
	public static void setAbstractPlayerMessageSender(AbstractPlayerMessageSender abstractPlayerMessageSender) {
		NovaCommons.abstractPlayerMessageSender = abstractPlayerMessageSender;
	}
	
	public static AbstractConsoleSender getAbstractConsoleSender() {
		return abstractConsoleSender;
	}

	public static void setAbstractConsoleSender(AbstractConsoleSender abstractConsoleSender) {
		NovaCommons.abstractConsoleSender = abstractConsoleSender;
	}
	
	public static AbstractSimpleTaskCreator getAbstractSimpleTaskCreator() {
		return abstractSimpleTaskCreator;
	}
	
	public static void setAbstractSimpleTaskCreator(AbstractSimpleTaskCreator abstractSimpleTaskCreator) {
		NovaCommons.abstractSimpleTaskCreator = abstractSimpleTaskCreator;
	}
	
	public static AbstractAsyncManager getAbstractAsyncManager() {
		return abstractAsyncManager;
	}
	
	public static void setAbstractAsyncManager(AbstractAsyncManager abstractAsyncRunner) {
		NovaCommons.abstractAsyncManager = abstractAsyncRunner;
	}
	
	public static void setServerType(ServerType serverType) {
		NovaCommons.serverType = serverType;
	}
	
	public static ServerType getServerType() {
		return serverType;
	}
}
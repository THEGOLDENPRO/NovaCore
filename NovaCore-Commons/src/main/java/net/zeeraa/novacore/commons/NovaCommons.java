package net.zeeraa.novacore.commons;

import net.zeeraa.novacore.commons.log.AbstractConsoleSender;
import net.zeeraa.novacore.commons.log.AbstractPlayerMessageSender;
import net.zeeraa.novacore.commons.tasks.AbstractSimpleTaskCreator;

public class NovaCommons {
	private static AbstractPlayerMessageSender abstractPlayerMessageSender;
	private static AbstractConsoleSender abstractConsoleSender;
	private static AbstractSimpleTaskCreator abstractSimpleTaskCreator;
	
	public static void setAbstractPlayerMessageSender(AbstractPlayerMessageSender abstractPlayerMessageSender) {
		NovaCommons.abstractPlayerMessageSender = abstractPlayerMessageSender;
	}
	
	public static AbstractPlayerMessageSender getAbstractPlayerMessageSender() {
		return abstractPlayerMessageSender;
	}
	
	public static void setAbstractConsoleSender(AbstractConsoleSender abstractConsoleSender) {
		NovaCommons.abstractConsoleSender = abstractConsoleSender;
	}
	
	public static AbstractConsoleSender getAbstractConsoleSender() {
		return abstractConsoleSender;
	}
	
	public static AbstractSimpleTaskCreator getAbstractSimpleTaskCreator() {
		return abstractSimpleTaskCreator;
	}
	
	public static void setAbstractSimpleTaskCreator(AbstractSimpleTaskCreator abstractSimpleTaskCreator) {
		NovaCommons.abstractSimpleTaskCreator = abstractSimpleTaskCreator;
	}
}
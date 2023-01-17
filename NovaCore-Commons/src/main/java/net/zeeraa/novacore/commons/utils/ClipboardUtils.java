package net.zeeraa.novacore.commons.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Utilities to interact with the servers clipboard
 * 
 * @author Zeeraa
 * @since 2.0.0
 */
public class ClipboardUtils {
	/**
	 * Set the content of the clipboard to the provided string
	 * 
	 * @param string The new clipboard content
	 */
	public static final void copyStringToClipboard(String string) {
		StringSelection stringSelection = new StringSelection(string);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
}
package xyz.zeeraa.novacore.module;

/**
 * Thrown when a module with an invalid name is loaded
 * 
 * @author Zeeraa
 */
public class InvalidModuleNameException extends RuntimeException {
	private static final long serialVersionUID = -7932909791043095964L;

	public InvalidModuleNameException() {
	}

	public InvalidModuleNameException(String message) {
		super(message);
	}

	public InvalidModuleNameException(Throwable cause) {
		super(cause);
	}

	public InvalidModuleNameException(String message, Throwable cause) {
		super(message, cause);
	}
}
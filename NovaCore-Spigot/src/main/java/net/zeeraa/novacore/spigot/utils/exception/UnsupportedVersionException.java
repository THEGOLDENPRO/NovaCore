package net.zeeraa.novacore.spigot.utils.exception;

public class UnsupportedVersionException extends Exception {
	private static final long serialVersionUID = 659870654315289296L;
	
	public UnsupportedVersionException() {
	}
	
	public UnsupportedVersionException(String message) {
		super(message);
	}
	
	public UnsupportedVersionException(Throwable cause) {
		super(cause);
	}
	
	public UnsupportedVersionException(String message, Throwable cause) {
		super(message, cause);
	}
}
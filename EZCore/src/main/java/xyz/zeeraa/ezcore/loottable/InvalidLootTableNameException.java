package xyz.zeeraa.ezcore.loottable;

public class InvalidLootTableNameException extends RuntimeException {
	private static final long serialVersionUID = -7932909791043095964L;

	public InvalidLootTableNameException() {
	}

	public InvalidLootTableNameException(String message) {
		super(message);
	}

	public InvalidLootTableNameException(Throwable cause) {
		super(cause);
	}

	public InvalidLootTableNameException(String message, Throwable cause) {
		super(message, cause);
	}
}
package net.zeeraa.novacore.spigot.mapdisplay;

public class MissingItemFrameException extends RuntimeException {
	private static final long serialVersionUID = 385030993727763933L;

	public MissingItemFrameException() {
		super();
	}

	public MissingItemFrameException(String message) {
		super(message);
	}

	public MissingItemFrameException(Throwable cause) {
		super(cause);
	}

	public MissingItemFrameException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingItemFrameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
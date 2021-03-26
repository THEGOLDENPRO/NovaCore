package net.zeeraa.novacore.spigot.module.modules.game;

/**
 * This is caused by trying to start a {@link MapGame} with no map loaded
 * 
 * @author Zeeraa
 */
public class NoMapsAddedException extends RuntimeException {
	private static final long serialVersionUID = -4221725908599688589L;

	public NoMapsAddedException() {
	}

	public NoMapsAddedException(String message) {
		super(message);
	}

	public NoMapsAddedException(Throwable cause) {
		super(cause);
	}

	public NoMapsAddedException(String message, Throwable cause) {
		super(message, cause);
	}
}
package xyz.zeeraa.ezcore.module.game;

/**
 * Represents the action to use when a a player quits
 * 
 * @author Zeeraa
 */
public enum PlayerQuitEliminationAction {
	/**
	 * Do not eliminate player on quit
	 */
	NONE,
	/**
	 * Eliminate player instantly on quit
	 */
	INSTANT,
	/**
	 * Eliminate the player after a certain delay. The delay can be set by changing
	 * {@link Game#getPlayerEliminationDelay()}
	 */
	DELAYED;
}
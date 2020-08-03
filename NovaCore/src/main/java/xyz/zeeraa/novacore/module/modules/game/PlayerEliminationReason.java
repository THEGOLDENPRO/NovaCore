package xyz.zeeraa.novacore.module.modules.game;

/**
 * Represents the reason why a player was removed from the game
 * 
 * @author Zeeraa
 */
public enum PlayerEliminationReason {
	/**
	 * The player was killed by another entity
	 */
	KILLED,
	/**
	 * The player died. If the player was killed by a entity
	 * {@link PlayerEliminationReason#KILLED} should be used instead
	 */
	DEATH,
	/**
	 * The player quit and was eliminated
	 */
	QUIT,
	/**
	 * The player quit and did not reconnect in time
	 */
	DID_NOT_RECONNECT,
	/**
	 * This can be used for custom elimination reasons
	 */
	OTHER;
}
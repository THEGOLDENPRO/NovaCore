package net.zeeraa.novacore.module.modules.game.elimination;

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
	 * The player died.
	 * <p>
	 * If the player was killed by a entity {@link PlayerEliminationReason#KILLED}
	 * should be used instead
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
	 * The player logged out while combat tagged
	 */
	COMBAT_LOGGING,
	/**
	 * This can be used for custom elimination reasons
	 */
	OTHER;
}
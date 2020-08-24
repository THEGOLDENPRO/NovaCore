package net.zeeraa.novacore.module.modules.game;

/**
 * Represents the reason why a game has ended
 * 
 * @author Zeeraa
 */
public enum GameEndReason {
	/**
	 * Used when there was no reason specified
	 */
	UNSPECIFIED,
	/**
	 * Used when a game takes too long time and ends
	 */
	TIME,
	/**
	 * Used when there was no winner
	 */
	DRAW,
	/**
	 * Used when a player has won the game
	 */
	WIN,
	/**
	 * Used when all players have completed the game.
	 * <p>
	 * This is used in games where all players have to complete a task in time and
	 * all players has finished
	 */
	ALL_FINISHED,
	/**
	 * Used when a operator uses a command to end the game
	 */
	OPERATOR_ENDED_GAME,
	/**
	 * Used when creating a game that ends for other reasons than the ones defined
	 * in {@link GameEndReason}
	 * <p>
	 * This is never used by any functions in the default NovaCore code
	 */
	SPECIAL_1,
	/**
	 * Used when creating a game that ends for other reasons than the ones defined
	 * in {@link GameEndReason}
	 * <p>
	 * This is never used by any functions in the default NovaCore code
	 */
	SPECIAL_2,
	/**
	 * Used when creating a game that ends for other reasons than the ones defined
	 * in {@link GameEndReason}
	 * <p>
	 * This is never used by any functions in the default NovaCore code
	 */
	SPECIAL_3,
	/**
	 * Used when creating a game that ends for other reasons than the ones defined
	 * in {@link GameEndReason}
	 * <p>
	 * This is never used by any functions in the default NovaCore code
	 */
	SPECIAL_4;
}
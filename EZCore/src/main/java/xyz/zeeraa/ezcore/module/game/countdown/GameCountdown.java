package xyz.zeeraa.ezcore.module.game.countdown;

import java.io.IOException;

import xyz.zeeraa.ezcore.module.game.GameManager;
import xyz.zeeraa.ezcore.module.gamelobby.GameLobby;

public abstract class GameCountdown {
	public abstract boolean hasCountdownStarted();

	public abstract boolean startCountdown();

	public abstract boolean cancelCountdown();

	protected void onCountdownFinished() throws IOException {
		if (GameManager.getInstance().isEnabled()) {
			if (GameManager.getInstance().hasGame()) {
				if (!GameManager.getInstance().getActiveGame().hasStarted()) {
					if (GameLobby.getInstance().isEnabled()) {
						GameLobby.getInstance().startGame();
					} else {
						GameManager.getInstance().start();
					}
				}
			}
		}
	}
}
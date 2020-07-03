package de.ben.BaseBuild.gamestates;

import de.ben.BaseBuild.countdowns.Countdown;
import de.ben.BaseBuild.countdowns.StartingCountdown;
import de.ben.BaseBuild.countdowns.WaitingCountdown;
import de.ben.BaseBuild.scoreboards.Board;
import de.ben.BaseBuild.scoreboards.LobbyScoreboard;

public class LobbyState implements GameState {
	
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 10;
	public static final int STANDARD_STARTING_TIME = 60;
	public static int CURRENT_COUNTDOWN = 60;
	
	private static Countdown waiting;
	private static Countdown starting;
	
	private static Board lobbyScoreboard;

	@Override
	public void start() {
		System.out.println("[BaseBuild] ENTERING LOBBYSTATE");
		
		waiting = new WaitingCountdown();
		starting = new StartingCountdown();
		
		waiting.start(0);
		
		lobbyScoreboard = new LobbyScoreboard();
		
		lobbyScoreboard.startUpdating();
	}

	@Override
	public void stop() {
		System.out.println("[BaseBuild] LEAVING LOBBYSTATE");
	}
	
	public static Countdown getStarting() {
		return starting;
	}
	
	public static Countdown getWaiting() {
		return waiting;
	}
	
	public static Board getLobbyScoreboard() {
		return lobbyScoreboard;
	}
}

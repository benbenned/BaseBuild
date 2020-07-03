package de.ben.BaseBuild.gamestates;

public class GameStateManager {
	
	GameState currentGameState = null;
	
	public GameStateManager() {
		
	}
	
	public void setGameState(GameState newGameState) {
		
		try {
			currentGameState.stop();
		} catch(Exception e) {
			System.out.println("[BaseBuild] LEAVING GAMESTATE NULL");
		}
		
		currentGameState = newGameState;
		newGameState.start();
	}
	
	public GameState getCurrentGameState() {
		
		return currentGameState;
	}
	
	public void stopAll() {
		
		try {
			currentGameState.stop();
		} catch(Exception e) {
			System.out.println("[ERROR STOP ALL] IDK Y");
		}
		currentGameState = null;
	}
}

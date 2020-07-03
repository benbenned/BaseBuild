package de.ben.BaseBuild.gamestates;

public class FightState implements GameState {

	@Override
	public void start() {
		System.out.println("[BaseBuild] ENTERING FIGHTSTATE");
		
		
	}

	@Override
	public void stop() {
		System.out.println("[BaseBuild] LEAVING FIGHTSTATE");
	}

}

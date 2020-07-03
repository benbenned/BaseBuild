package de.ben.BaseBuild.scoreboards;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class BuildScoreboard extends Board {

	@Override
	public void setScoreboard(Player player) {
		
		Scoreboard b = player.getScoreboard();
		Objective o = b.getObjective("aaa") != null ? b.getObjective("aaa") : b.registerNewObjective("aaa", "dummy");
		
		
	}

	@Override
	public void startUpdating() {
		
		
	}

	
}

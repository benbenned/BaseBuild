package de.ben.BaseBuild.countdowns;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import de.ben.BaseBuild.gamestates.BuildState;
import de.ben.BaseBuild.gamestates.LobbyState;
import de.ben.BaseBuild.main.Main;

public class StartingCountdown extends Countdown {

	@Override
	public void start(int seconds) {
		
		isRunning = true;
		count = seconds + 1;
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				
				count--;
				
				for(Player current : Main.getPlayers()) {
					
					current.setLevel(count);
					
					Scoreboard board = current.getScoreboard();
					
					board.getTeam("countdown").setPrefix("§a§lStarting in ");
					board.getTeam("countdown").setSuffix("§f" + count);
				}
				
				switch(count) {
				
				case 60: case 50: case 40: case 30: case 20: case 10: case 5:
					Bukkit.broadcastMessage(Main.PREFIX + "§7The game starts in §6" + count + " §7seconds!");
					break;
					
				case 0:
					Bukkit.broadcastMessage(Main.PREFIX + "§7The game starts!");
					Bukkit.getScheduler().cancelTask(taskID);
					
					Main.getGameStateManager().setGameState(new BuildState());
					break;
				
				default:
					break;
				}
			}
		}, 0L, 20 * 1);
	}

	@Override
	public void stop() {
		
		isRunning = false;
		
		for(Player current : Main.getPlayers()) {
			
			Scoreboard board = current.getScoreboard();
			
			board.getTeam("countdown").setPrefix("§c§lWaiting...");
			board.getTeam("countdown").setSuffix("§f");
		}
		
		Bukkit.getScheduler().cancelTask(taskID);
	}
	
	public int getCount() {
		
		return isRunning ? count : LobbyState.STANDARD_STARTING_TIME;
	}

}

package de.ben.BaseBuild.countdowns;

import org.bukkit.Bukkit;

import de.ben.BaseBuild.gamestates.FightState;
import de.ben.BaseBuild.main.Main;

public class BuildingCountdown extends Countdown {

	@Override
	public void start(int seconds) {
		
		isRunning = true;
		count = seconds + 1;
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				
				count--;
				
				System.out.println("ICH ZÄHLE RUNNER" + count);
				
				switch(count) {
				
				case 120: case 60: case 30: case 10: case 5:
					Bukkit.broadcastMessage(Main.PREFIX + "§7The building period ends in §6" + count + " §7seconds!");
					break;
				
				case 0:
					Bukkit.getScheduler().cancelTask(taskID);
					
					Main.getGameStateManager().setGameState(new FightState());
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
		
		Bukkit.getScheduler().cancelTask(taskID);
	}

}

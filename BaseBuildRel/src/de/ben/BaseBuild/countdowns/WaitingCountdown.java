package de.ben.BaseBuild.countdowns;

import org.bukkit.Bukkit;

import de.ben.BaseBuild.gamestates.LobbyState;
import de.ben.BaseBuild.main.Main;

public class WaitingCountdown extends Countdown {
	
	@Override
	public void start(int seconds) {
		
		isRunning = true;
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				
				int missingPlayers = LobbyState.MIN_PLAYERS - Main.getPlayers().size();
				
				if(missingPlayers == 1) {
					Bukkit.broadcastMessage(Main.PREFIX + "§7Waiting for §cone §7more player...");
				} else {
					Bukkit.broadcastMessage(Main.PREFIX + "§7Waiting for §c" + missingPlayers + " §7more players...");
				}
				
			}
		}, 20 * 5L, 20 * 20);
	}

	@Override
	public void stop() {
		
		isRunning = false;
		
		Bukkit.getScheduler().cancelTask(taskID);
		
	}
	
}

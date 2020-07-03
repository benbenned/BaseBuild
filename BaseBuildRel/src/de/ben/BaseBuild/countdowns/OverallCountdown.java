package de.ben.BaseBuild.countdowns;

import org.bukkit.Bukkit;

import de.ben.BaseBuild.main.Main;

public class OverallCountdown extends Countdown {

	@Override
	public void start(int seconds) {
		
		isRunning = true;
		
		count = seconds;
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				
				count++;
			}
		}, 0, 20 * 1);
	}

	@Override
	public void stop() {
		
		isRunning = false;
		
		Bukkit.getScheduler().cancelTask(taskID);
	}

}

package de.ben.BaseBuild.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class Board {

	protected int taskID;
	
	public abstract void setScoreboard(Player player);
	public abstract void startUpdating();
	
	public void stopUpdating() {
		
		System.out.println("STOPPED BOARDSCHEDULER");
		Bukkit.getScheduler().cancelTask(taskID);
	}
}

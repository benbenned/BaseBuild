package de.ben.BaseBuild.stats;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.utils.Hologram;
import de.ben.BaseBuild.utils.PlayersMethods;

public class StatsManager {
	
	private HashMap<Player, Integer> kills;
	private HashMap<Player, Integer> deaths;
	
	private HashMap<Player, Hologram> holograms; 
	
	public StatsManager() {
		
		kills = new HashMap<>();
		deaths = new HashMap<>();
		holograms = new HashMap<>();
	}

	public String[] getStatsToShow(String rndmName) {

		// MUST BE VALIDATED IF PLAYER IS SERVER APPENDEE

		if(hasStats(rndmName)) {
			
			String uuid = PlayersMethods.tryGetUUID(rndmName);
			
			System.out.println("[INFO] GETTING UUID:" + uuid);
			
			String statsS = Main.getSf().getString(uuid);

			String[] stats = statsS.split(";");

			return stats;
		}
		
		String[] zero = {"0","0","0","0","0"};
		
		return zero;
	}

	public List<String> getStatsToEdit(String rndmName) {

		// ONLY TO CHANGE THE STATS

		if(hasStats(rndmName)) {
			
			String[] statsArray = getStatsToShow(rndmName);

			List<String> stats = Arrays.asList(statsArray);

			return stats;
		}
		return null;

	}

	public void setStats(String rndmName, int kills, int deaths, boolean isWinning) {

		List<String> stats = getStatsToEdit(rndmName);

		int points = Integer.parseInt(stats.get(0));
		int games = Integer.parseInt(stats.get(1));
		int wins = Integer.parseInt(stats.get(2));
		int kill = Integer.parseInt(stats.get(3));
		int death = Integer.parseInt(stats.get(4));
		
		points += kills * 10;
		if (isWinning) {
			points += 100;
		}
		if (isWinning) {
			wins += 1;
		}
		kill += kills;

		death += deaths;

		String finalStats = points + ";" + games + ";" + wins + ";" + kill + ";" + death;

		Main.getSf().setValue(PlayersMethods.tryGetUUID(rndmName), finalStats);
		Main.getSf().save();
	}

	public void setGame(String rndmName) {

		List<String> stats = getStatsToEdit(rndmName);

		int points = Integer.parseInt(stats.get(0));
		int games = Integer.parseInt(stats.get(1));
		int wins = Integer.parseInt(stats.get(2));
		int kill = Integer.parseInt(stats.get(3));
		int death = Integer.parseInt(stats.get(4));

		games += 1;

		points += 5;

		String finalStats = points + ";" + games + ";" + wins + ";" + kill + ";" + death;

		Main.getSf().setValue(PlayersMethods.tryGetUUID(rndmName), finalStats);
		Main.getSf().save();
	}

	public double getKD(int kills, int deaths) {

		double kd = 0.0;
		
		if(deaths != 0) {
			kd = (double) kills / (double) deaths * 1000;
		}
		
		kd = Math.round(kd);
		kd /= 1000;

		return kd;
	}

	public double getWinRate(int games, int wins) {

		double winrate = 0.0;
		
		if(games != 0) {
			winrate = (double) wins / (double) games * 1000;
		}
		
		winrate = Math.round(winrate);
		winrate /= 10;

		return winrate;
	}

	public boolean hasStats(String rndmName) {

		String uuid = PlayersMethods.tryGetUUID(rndmName);

		return Main.getSf().getString(uuid) != null ? true : false;
	}
	
	public void buildStats(String rndmName) {
		
		String uuid = PlayersMethods.tryGetUUID(rndmName);
		
		Main.getSf().setValue(uuid, "0;0;0;0;0");
		Main.getSf().save();
	}
	
	public HashMap<Player, Integer> getKills() {
		return kills;
	}
	
	public HashMap<Player, Integer> getDeaths() {
		return deaths;
	}
	
	public HashMap<Player, Hologram> getHolograms() {
		return holograms;
	}
}

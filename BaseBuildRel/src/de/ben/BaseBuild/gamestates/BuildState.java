package de.ben.BaseBuild.gamestates;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.ben.BaseBuild.countdowns.BuildingCountdown;
import de.ben.BaseBuild.countdowns.Countdown;
import de.ben.BaseBuild.countdowns.OverallCountdown;
import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.stats.StatsManager;
import de.ben.BaseBuild.teams.Team;
import de.ben.BaseBuild.teams.TeamManager;
import de.ben.BaseBuild.utils.ItemManager;
import de.ben.BaseBuild.utils.LocationManager;

public class BuildState implements GameState {
	
	private StatsManager sm;
	private ItemManager im;
	
	private static LocationManager locationManager;
	
	private static Countdown building;
	private static Countdown overall;
	
	@Override
	public void start() {
		System.out.println("[BaseBuild] ENTERING BUILDSTATE");
		
		locationManager = new LocationManager(Team.values());
		
		TeamManager tm = Main.getTeamManager();
		sm = new StatsManager();
		im = new ItemManager();
		
		building = new BuildingCountdown();
		overall = new OverallCountdown();
		
		LobbyState.getLobbyScoreboard().stopUpdating();
		
		building.start(120);
		overall.start(0);
		
		for(Player current : Main.getPlayers()) {
			
			if(!tm.isTeamAppendee(current)) {
				
				tm.setRandomTeam(current);
			}
			
			String name = tm.getPlayersRealName(current);

			if (sm.hasStats(name)) {
				sm.setGame(name);
			} else {
				sm.buildStats(name);
			}
			
			Main.getStatsManager().getKills().put(current, 0);
			Main.getStatsManager().getDeaths().put(current, 0);
			
			System.out.println("name: " + name);
			
			current.getInventory().clear();
		}
		
		tm.makeTeamsRight();
		
		Bukkit.broadcastMessage(Main.PREFIX + "§aAll players will be teleported!");
		
		for(Player p : Main.getPlayers()) {
			
			im.setBuildItems(p);
			
			tm.setTeamNameAboveHead(p);
		}
		
		locationManager.teleportAllTeams();
	}

	@Override
	public void stop() {
		System.out.println("[BaseBuild] LEAVING BUILDSTATE");
	}
	
	public static Countdown getBuilding() {
		return building;
	}
	
	public static Countdown getOverall() {
		return overall;
	}
	
	public static LocationManager getLocationManager() {
		return locationManager;
	}

}

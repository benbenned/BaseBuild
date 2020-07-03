package de.ben.BaseBuild.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.ben.BaseBuild.commands.SetCordsCommand;
import de.ben.BaseBuild.commands.StartCommand;
import de.ben.BaseBuild.commands.StatsCommand;
import de.ben.BaseBuild.filebuilder.FileBuilder;
import de.ben.BaseBuild.gamestates.GameStateManager;
import de.ben.BaseBuild.gamestates.LobbyState;
import de.ben.BaseBuild.listeners.BuildListener;
import de.ben.BaseBuild.listeners.ChatListener;
import de.ben.BaseBuild.listeners.LoginListener;
import de.ben.BaseBuild.listeners.LogoutListener;
import de.ben.BaseBuild.listeners.PlayersJoinListener;
import de.ben.BaseBuild.listeners.SpectatorListener;
import de.ben.BaseBuild.listeners.LobbyListener;
import de.ben.BaseBuild.stats.StatsManager;
import de.ben.BaseBuild.teams.TeamManager;
import de.ben.BaseBuild.utils.Spectators;

public class Main extends JavaPlugin {
	
	private static Main plugin;
	
	private static GameStateManager gameStateManager;
	private static TeamManager teamManager;
	private static StatsManager statsManager;
	
	private static Spectators spectators;
	
	private static HashMap<String, String> names;
	private static HashMap<String, String> uuids;
	
	private static FileBuilder pf;
	private static FileBuilder sf;
	private static FileBuilder bf;
	
	private static List<Player> players = new ArrayList<>(); 
	private static List<Player> specs = new ArrayList<>();
	
	public static final String PREFIX = "§7[" + ChatColor.AQUA + "BaseBuild§7] ";
	public static final String STATS_PREFIX = "§7[" + ChatColor.AQUA + "Stats§7] ";
	
	public void onEnable() {
		
		plugin = this;
		System.out.println("[BaseBuild] ENABLED");
		
		includeListeners();
		startMaps();
		
		gameStateManager = new GameStateManager();
		gameStateManager.setGameState(new LobbyState());
		
		teamManager = new TeamManager();
		statsManager = new StatsManager();
		
		spectators = new Spectators();
		
		includeFiles();
		
		getCommand("stats").setExecutor(new StatsCommand());
		getCommand("start").setExecutor(new StartCommand());
		getCommand("setcords").setExecutor(new SetCordsCommand());
		
		if(!pf.exists()) {
			System.out.println("Peter");
			startPf();
		}
		
		if(!sf.exists()) {
			System.out.println("Günther");
			startSf();
		}
		
		if(!bf.exists()) {
			System.out.println("Torsten");
			startBf();
		}
		
		names = pf.getHashMap("NAMES");
		uuids = pf.getHashMap("UUIDS");
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect @a 23 1000000 20 true");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule keepInventory true");
		
		System.out.println(names.toString() + "   " + uuids.toString());
	}
	
	public void onDisable() {
		
		System.out.println("[BaseBuild] DISABLED");
		
		try {
			gameStateManager.stopAll();
		} catch(Exception e) {
			System.out.println("LEAVING GAMESTATE NULL");
		}
		
		System.out.println("Setting Maps...");
		
		pf.setHashMap("NAMES", names);
		pf.setHashMap("UUIDS", uuids);
		pf.save();
		
		System.out.println("Setting Maps finished!");
		
	}
	
	private void includeListeners() {
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new LoginListener(), getPlugin());
		pm.registerEvents(new LogoutListener(), getPlugin());
		pm.registerEvents(new PlayersJoinListener(), getPlugin());
		pm.registerEvents(new ChatListener(), getPlugin());
		pm.registerEvents(new LobbyListener(), getPlugin());
		pm.registerEvents(new SpectatorListener(), getPlugin());
		pm.registerEvents(new BuildListener(), getPlugin());
		
	}
	
	private void includeFiles() {
		
		pf = new FileBuilder("plugins//BaseBuild//", "players.yml");
		sf = new FileBuilder("plugins//BaseBuild//", "stats.yml");
		bf = new FileBuilder("plugins//BaseBuild//", "base.yml");
	}
	
	private void startMaps() {
		
		names = new HashMap<>();
		uuids = new HashMap<>();
	}
	
	private void startPf() {
		
		names.put("horsti", "horsti");
		uuids.put("horsti", "horsti");
		
		pf.setHashMap("UUIDS", uuids);
		pf.setHashMap("NAMES", names);
		pf.save();
	}
	
	private void startSf() {
		
		sf.setValue("horsti", "0;0;0;0;0");
		sf.save();
	}
	
	private void startBf() {
		
		bf.setValue("CORDS.test", "horsti");
		bf.save();
	}
	
	public static Main getPlugin() {
		return plugin;
	}
	
	public static HashMap<String, String> getUuids() {
		return uuids;
	}
	
	public static HashMap<String, String> getNames() {
		return names;
	}
	
	public static FileBuilder getPf() {
		return pf;
	}
	
	public static FileBuilder getSf() {
		return sf;
	}
	
	public static FileBuilder getBf() {
		return bf;
	}
	
	public static GameStateManager getGameStateManager() {
		return gameStateManager;
	}
	
	public static List<Player> getPlayers() {
		return players;
	}
	
	public static TeamManager getTeamManager() {
		return teamManager;
	}
	
	public static List<Player> getSpecs() {
		return specs;
	}
	
	public static Spectators getSpectators() {
		return spectators;
	}
	
	public static StatsManager getStatsManager() {
		return statsManager;
	}
}

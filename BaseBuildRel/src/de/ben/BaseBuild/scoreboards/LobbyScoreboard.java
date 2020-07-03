package de.ben.BaseBuild.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.ben.BaseBuild.gamestates.LobbyState;
import de.ben.BaseBuild.main.Main;

public class LobbyScoreboard extends Board {

	@Override
	public void setScoreboard(Player player) {

		Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = b.getObjective("aaa") != null ? b.getObjective("aaa") : b.registerNewObjective("aaa", "dummy");

		o.setDisplayName("§b§lBASEBUILD");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		
		Team empty1 = b.registerNewTeam("empty1");
		
		empty1.addEntry(ChatColor.DARK_GREEN.toString());
		
		o.getScore(ChatColor.DARK_GREEN.toString()).setScore(10);
		
		Team players = b.registerNewTeam("players");
		
		players.addEntry(ChatColor.DARK_PURPLE.toString());
		players.setPrefix("§6§lPlayers:");
		
		o.getScore(ChatColor.DARK_PURPLE.toString()).setScore(9);
		
		Team playerInt = b.registerNewTeam("playerInt");
		
		playerInt.setSuffix("§f" + Main.getPlayers().size() + "/" + LobbyState.MAX_PLAYERS);
		playerInt.addEntry(ChatColor.BLACK.toString());
		
		o.getScore(ChatColor.BLACK.toString()).setScore(8);
		
		Team empty2 = b.registerNewTeam("empty2");
		
		empty2.addEntry(ChatColor.DARK_RED.toString());
		
		o.getScore(ChatColor.DARK_RED.toString()).setScore(7);
		
		Team yourTeam = b.registerNewTeam("yourTeam");
		
		yourTeam.addEntry(ChatColor.AQUA.toString());
		yourTeam.setPrefix("§6§lYour team:");
		
		o.getScore(ChatColor.AQUA.toString()).setScore(6);
		
		Team playersTeam = b.registerNewTeam("playersTeam");
		
		playersTeam.setSuffix(Main.getTeamManager().getPlayersTeamName(player));
		playersTeam.addEntry(ChatColor.DARK_BLUE.toString());
		
		o.getScore(ChatColor.DARK_BLUE.toString()).setScore(5);
		
		Team empty3 = b.registerNewTeam("empty3");
		
		empty3.addEntry(ChatColor.BLUE.toString());
		
		o.getScore(ChatColor.BLUE.toString()).setScore(4);
		
		Team countdown = b.registerNewTeam("countdown");
		
		countdown.setPrefix("§c§lWaiting...");
		countdown.setSuffix("§f");
		countdown.addEntry(ChatColor.DARK_GRAY.toString());
		
		o.getScore(ChatColor.DARK_GRAY.toString()).setScore(3);
		
		Team empty4 = b.registerNewTeam("empty4");
		
		empty4.addEntry(ChatColor.GOLD.toString());
		
		o.getScore(ChatColor.GOLD.toString()).setScore(2);
		
		Team lines = b.registerNewTeam("lines");
		
		lines.addEntry(ChatColor.GRAY.toString());
		lines.setPrefix("----------------");
		
		o.getScore("----------------").setScore(1);
		
		player.setScoreboard(b);
	}

	@Override
	public void startUpdating() {
		
		 BukkitTask updater = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				
				for(Player current : Main.getPlayers()) {
					
					Scoreboard board = current.getScoreboard();
					
					board.getTeam("playerInt").setSuffix("§f" + Main.getPlayers().size() + "/" + LobbyState.MAX_PLAYERS);
					board.getTeam("playersTeam").setSuffix(Main.getTeamManager().getPlayersTeamName(current));
				}
			}
		}, 0, 10L);
		 
		 taskID = updater.getTaskId();
	}
}

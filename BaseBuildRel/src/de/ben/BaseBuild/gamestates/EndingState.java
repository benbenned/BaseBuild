package de.ben.BaseBuild.gamestates;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.stats.StatsManager;
import de.ben.BaseBuild.teams.Team;

public class EndingState implements GameState {

	@Override
	public void start() {
		System.out.println("[BaseBuild] ENTERING ENDINGSTATE");

		if (BuildState.getBuilding().isRunning()) {

			BuildState.getBuilding().stop();
		}

		int[] time = getTime(BuildState.getOverall().getCount());

		Bukkit.broadcastMessage("§7The round took §6" + time[0] + " §7minutes and §6" + time[1] + " §7seconds!");

		BuildState.getOverall().stop();

		for (Player current : Main.getPlayers()) {

			current.teleport(BuildState.getLocationManager().getLocation("LOBBY"));

			if (Main.getSpectators().isSpectator(current)) {

				Main.getTeamManager().removePlayer(current);
				Main.getTeamManager().removeTeamNameAboveHead(current);
			}
		}

		for (Team t : Team.values()) {

			for (Player current : t.getPlayers()) {

				StatsManager sm = Main.getStatsManager();
				Team cTeam = Main.getTeamManager().getPlayersTeam(current);
				int kills = Main.getStatsManager().getKills().get(current);
				int deaths = Main.getStatsManager().getDeaths().get(current);
				boolean won = cTeam == Main.getTeamManager().getWinning() ? true : false;

				sm.setStats(Main.getTeamManager().getNameFinder().get(current.getName()), kills, deaths, won);

				current.sendMessage(Main.STATS_PREFIX + "§7Statistics of that round:");
				current.sendMessage(Main.STATS_PREFIX + "§7Kills: §6" + kills);
				current.sendMessage(Main.STATS_PREFIX + "§7Deaths: §6" + deaths);

				if (won) {
					current.sendMessage(Main.STATS_PREFIX + "§7Won: §aYes");
				} else {
					current.sendMessage(Main.STATS_PREFIX + "§7Won: §cNo");
				}

				current.sendMessage(Main.PREFIX + "§aThank you §7for playing §bBaseBuild§7!");
			}
		}
	}

	@Override
	public void stop() {
		System.out.println("[BaseBuild] LEAVING ENDINGSTATE");
	}

	private int[] getTime(int seconds) {

		int min = (int) Math.floor(seconds / 60);
		int sec = seconds - min * 60;

		int[] time = { min, sec };

		return time;
	}

}

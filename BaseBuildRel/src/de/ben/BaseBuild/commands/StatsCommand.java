package de.ben.BaseBuild.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.stats.StatsManager;
import de.ben.BaseBuild.utils.PlayersMethods;

public class StatsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to execute this command.");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(args.length > 1) {
			p.sendMessage("§cUsage: /stats [Player]");
			return true;
		}
		
		if(args.length == 0) {
			
			showStats(p, p.getName());
			return true;
		}
		
		if(!PlayersMethods.isServerAppendeeByName(args[0])) {
			p.sendMessage(Main.PREFIX + "§cThat player never played BaseBuild!");
			return true;
		}
		
		p.sendMessage(Main.STATS_PREFIX + "§7Receiving information...");
		
		showStats(p, args[0]);
		return true;
	}
	
	private void showStats(Player player, String rndmName) {
		
		StatsManager statsManager = Main.getStatsManager();
		
		String[] stats = statsManager.getStatsToShow(rndmName);
		String name = PlayersMethods.tryGetRealName(rndmName);
		
		double kd = statsManager.getKD(Integer.parseInt(stats[3]), Integer.parseInt(stats[4]));
		double winrate = statsManager.getWinRate(Integer.parseInt(stats[1]), Integer.parseInt(stats[2]));
		
		player.sendMessage(Main.STATS_PREFIX + "§7Statistics of §6" + name + " §8(All time)");
		player.sendMessage(Main.STATS_PREFIX + "  §7Points: §6" + stats[0]);
		player.sendMessage(Main.STATS_PREFIX + "  §7Played: §6" + stats[1]);
		player.sendMessage(Main.STATS_PREFIX + "  §7Won: §6" + stats[2]);
		player.sendMessage(Main.STATS_PREFIX + "  §7Kills: §6" + stats[3]);
		player.sendMessage(Main.STATS_PREFIX + "  §7Deaths: §6" + stats[4]);
		player.sendMessage("§7--------------------------");
		player.sendMessage(Main.STATS_PREFIX + "  §7K/D: §6" + kd);
		player.sendMessage(Main.STATS_PREFIX + "  §7W/P: §6" + winrate + "%");
		player.sendMessage("§7--------------------------");
	}

}

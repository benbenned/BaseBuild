package de.ben.BaseBuild.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ben.BaseBuild.gamestates.GameStateManager;
import de.ben.BaseBuild.gamestates.LobbyState;
import de.ben.BaseBuild.main.Main;

public class StartCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		GameStateManager gsm = Main.getGameStateManager();

		if (!(gsm.getCurrentGameState() instanceof LobbyState)) {
			sender.sendMessage("Unknown command. Type \"/help\" for help.");
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to execute this command.");
			return true;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("BASEBUILD_START")) {
			p.sendMessage(Main.PREFIX + "§cYou don't have the permission to execute this command.");
			return true;
		}

		if (args.length != 0) {
			p.sendMessage("§cUsage: /start");
			return true;
		}

		if (Main.getPlayers().size() < LobbyState.MIN_PLAYERS || Main.getPlayers().size() > LobbyState.MAX_PLAYERS) {
			p.sendMessage(Main.PREFIX
					+ "§cI'm sorry, but starting the game ist not possible because of an unusal amount of players.");
			return true;
		}
		
		if (LobbyState.getStarting().getCount() <= 10) {
			p.sendMessage(Main.PREFIX + "§cGame is already starting...");
			return true;
		}
		
		LobbyState.getStarting().stop();
		LobbyState.getStarting().start(10);
		p.sendMessage(Main.PREFIX + "§aStarting game...");
		return true;
	}

}

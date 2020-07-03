package de.ben.BaseBuild.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.ben.BaseBuild.gamestates.BuildState;
import de.ben.BaseBuild.gamestates.FightState;
import de.ben.BaseBuild.gamestates.GameStateManager;
import de.ben.BaseBuild.gamestates.LobbyState;
import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.teams.Team;

public class LogoutListener implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {

		GameStateManager gsm = Main.getGameStateManager();
		Player p = e.getPlayer();

		Main.getPlayers().remove(p);
		
		if(Main.getStatsManager().getHolograms().containsKey(p)) {
			
			Main.getStatsManager().getHolograms().remove(p);
		}
		
		if(Main.getTeamManager().isTeamAppendee(p)) {
			
			Main.getTeamManager().removePlayer(p);
			Main.getTeamManager().getNameFinder().remove(p.getName());
		}
		
		p.getInventory().clear();
		
		if(Main.getSpectators().isSpectator(p)) {
			
			e.setQuitMessage(null);
			Main.getSpecs().remove(p);
		}
		
		if (gsm.getCurrentGameState() instanceof LobbyState) {

			e.setQuitMessage(Main.PREFIX + "§6" + p.getName() + " §7left the game! §8(" + Main.getPlayers().size() + "/"
					+ LobbyState.MAX_PLAYERS + ")");
			
			if (Main.getPlayers().size() == LobbyState.MIN_PLAYERS - 1) {
				
				LobbyState.CURRENT_COUNTDOWN = LobbyState.getStarting().getCount();
				
				LobbyState.getStarting().stop();
				LobbyState.getWaiting().start(0);
			}
		} else if(gsm.getCurrentGameState() instanceof BuildState || gsm.getCurrentGameState() instanceof FightState) {
			
			if(!Main.getTeamManager().areEnoughTeamsFilled()) {
				
				Team winning = Main.getTeamManager().getPlayersTeam(Main.getPlayers().get(0));
				
				Main.getTeamManager().win(winning);
			}
		}
	}
}

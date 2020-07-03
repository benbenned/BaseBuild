package de.ben.BaseBuild.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import de.ben.BaseBuild.gamestates.BuildState;
import de.ben.BaseBuild.gamestates.EndingState;
import de.ben.BaseBuild.gamestates.FightState;
import de.ben.BaseBuild.gamestates.GameStateManager;
import de.ben.BaseBuild.gamestates.LobbyState;
import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.utils.ItemManager;
import de.ben.BaseBuild.utils.LocationManager;

public class LoginListener implements Listener {
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		
		if(Main.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
			
			if(Main.getPlayers().size() >= LobbyState.MAX_PLAYERS) {
				e.disallow(PlayerLoginEvent.Result.KICK_FULL, "The server is full!");
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		GameStateManager gsm = Main.getGameStateManager();
		Player p = e.getPlayer();
		
		ItemManager im = new ItemManager();
		
		Main.getPlayers().add(p);
		
		p.setLevel(0);

		if (gsm.getCurrentGameState() instanceof LobbyState) {
			
			e.setJoinMessage(Main.PREFIX + "§6" + p.getName() + " §7joined the game! §8(" + Main.getPlayers().size()
					+ "/" + LobbyState.MAX_PLAYERS + ")");
			
			Main.getTeamManager().getNameFinder().put(p.getName(), p.getName());
			
			im.setSelectorItems(p);
			
			p.teleport(LocationManager.getLobbyLocation());
			
			LobbyState.getLobbyScoreboard().setScoreboard(p);
			
			if(Main.getPlayers().size() == LobbyState.MIN_PLAYERS) {
				
				LobbyState.getStarting().start(LobbyState.CURRENT_COUNTDOWN);
				LobbyState.getWaiting().stop();
			}
		} else if(gsm.getCurrentGameState() instanceof BuildState || gsm.getCurrentGameState() instanceof FightState) {
			
			Main.getSpecs().add(p);
			
			p.setPlayerListName("§8" + p.getName());
			
			for(Player current : Main.getPlayers()) {
				
				current.hidePlayer(p);
			}
			
			im.setSpectatorItems(p);
		} else if(gsm.getCurrentGameState() instanceof EndingState) {
			
		}
	}
}

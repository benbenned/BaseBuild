package de.ben.BaseBuild.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.ben.BaseBuild.gamestates.BuildState;
import de.ben.BaseBuild.gamestates.EndingState;
import de.ben.BaseBuild.gamestates.FightState;
import de.ben.BaseBuild.gamestates.GameStateManager;
import de.ben.BaseBuild.gamestates.LobbyState;
import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.teams.Team;
import de.ben.BaseBuild.teams.TeamManager;

public class ChatListener implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		
		Player p = e.getPlayer();
		String msg = e.getMessage();
		GameStateManager gsm = Main.getGameStateManager();
		TeamManager tm = Main.getTeamManager();
		
		e.setCancelled(true);
		
		if(gsm.getCurrentGameState() instanceof LobbyState || gsm.getCurrentGameState() instanceof EndingState) {
			
			if(!tm.isTeamAppendee(p)) {
				
				msg = "§6" + p.getName() + " §8> §7" + msg;
				Bukkit.broadcastMessage(msg);
				
				return;
			}
			
			Team team = tm.getPlayersTeam(p);
			
			msg = team.getChatColor() + p.getName() + " §8> §7" + msg;
			
			Bukkit.broadcastMessage(msg);
			
		} else if(gsm.getCurrentGameState() instanceof BuildState || gsm.getCurrentGameState() instanceof FightState) {
			
			if(!Main.getSpectators().isSpectator(p)) {
				
				Team team = tm.getPlayersTeam(p);
				
				if(msg.startsWith("@a")) {
					
					msg = msg.substring(2);
					msg = msg.trim();
					
					Bukkit.broadcastMessage("§7[GLOBAL] " + team.getChatColor() + p.getName() + " §8> §7" + msg);
				} else {
					
					tm.sendTeamMessage("§7[TEAM] " + team.getChatColor() + p.getName() + " §8> §7" + msg, team);
				}
			} else {
				
				Main.getSpectators().sendSpecMessage("§8" + p.getName() + " > " + msg);
			}
		}
	}
}

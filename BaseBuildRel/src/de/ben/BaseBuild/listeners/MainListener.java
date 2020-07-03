package de.ben.BaseBuild.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import de.ben.BaseBuild.gamestates.BuildState;
import de.ben.BaseBuild.gamestates.EndingState;
import de.ben.BaseBuild.gamestates.FightState;
import de.ben.BaseBuild.gamestates.LobbyState;
import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.teams.Team;
import de.ben.BaseBuild.utils.ItemManager;
import de.ben.BaseBuild.utils.LocationManager;

public class MainListener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {

		if (Main.getGameStateManager().getCurrentGameState() instanceof FightState) {

			
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect @a 23 1000000 20 true");

		if (Main.getGameStateManager().getCurrentGameState() instanceof LobbyState
				|| Main.getGameStateManager().getCurrentGameState() instanceof EndingState) {

			Player p = e.getPlayer();
			
			p.teleport(LocationManager.getLobbyLocation());
		} else if(Main.getGameStateManager().getCurrentGameState() instanceof BuildState) {
			
			Player p = e.getPlayer();
			Team t = Main.getTeamManager().getPlayersTeam(p);
			
			p.teleport(BuildState.getLocationManager().getLocation(t.getLocName()));
		} else if(Main.getGameStateManager().getCurrentGameState() instanceof FightState) {
			
			Player p = e.getPlayer();
			
			p.getInventory().clear();
			
			ItemManager im = new ItemManager();
			
			im.setFightItems(p);
		}
	}
}

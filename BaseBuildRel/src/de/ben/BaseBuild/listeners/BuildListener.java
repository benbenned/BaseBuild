package de.ben.BaseBuild.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.ben.BaseBuild.gamestates.BuildState;
import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.utils.LocationManager;

public class BuildListener implements Listener {

	@EventHandler
	public void onBuild(BlockPlaceEvent e) {
		
		if(Main.getGameStateManager().getCurrentGameState() instanceof BuildState) {
			
			if(e.getBlock().getType() == Material.WOOL) {
				
				Location teamSpawn = BuildState.getLocationManager().getLocation(Main.getTeamManager().getPlayersTeam(e.getPlayer()));
				
				int x, y, z;
				
				x = e.getBlock().getX();
				y = e.getBlock().getY();
				z = e.getBlock().getZ();
				
				Location loc = new Location(e.getPlayer().getWorld(), x, y - 1, z);
				Location loc2 = new Location(e.getPlayer().getWorld(), x, y - 2, z);
				
				if(loc.getBlock().getType() == Material.QUARTZ_BLOCK || loc2.getBlock().getType() == Material.QUARTZ_BLOCK) {
					
					e.setCancelled(true);
					return;
				}
				
				if(LocationManager.getDistance(x, (int) teamSpawn.getX() - 1) > 4) {
					
					System.out.println(x + ", " + (int) teamSpawn.getX() + " User: " +  (int) e.getPlayer().getLocation().getX());
					e.setCancelled(true);
					return;
				}
				
				if(LocationManager.getDistance(y, (int) teamSpawn.getY() - 1) > 8) {
					
					e.setCancelled(true);
					return;
				}
				
				if(LocationManager.getDistance(z, (int) teamSpawn.getZ() - 1) > 4) {
					
					e.setCancelled(true);
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		
		if(Main.getGameStateManager().getCurrentGameState() instanceof BuildState) {
			
			if(e.getBlock().getType() == Material.STAINED_CLAY || e.getBlock().getType() == Material.CLAY || e.getBlock().getType() == Material.QUARTZ_BLOCK) {
				
				e.setCancelled(true);
			}
		}
	}
}

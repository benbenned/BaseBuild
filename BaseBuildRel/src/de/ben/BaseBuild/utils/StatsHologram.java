package de.ben.BaseBuild.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.ben.BaseBuild.main.Main;
import net.minecraft.server.v1_8_R3.EntityBat;
import net.minecraft.server.v1_8_R3.World;

public class StatsHologram {

	private String[] text;
	private Location location;
	private Location currentLocation;
	private double distance;
	private List<Entity> entities;
	private boolean isVisible = false;
	
	private static final int STATS_DISTANCE = 3;
	
	/**
	 * 
	 * @param text - Text that will be shown
	 * @param location - Location where Hologram will be spawned
	 * @param distance - Distance between two lines
	 */
	
	public StatsHologram(String[] text, Location location, double distance) {
		
		this.text = text;
		this.location = location;
		this.distance = distance;
		
		entities = new ArrayList<>();
	}
	
	private void build() {
		
		for(String line : text) {
			
			EntityBat bat = new EntityBat((World) location.getWorld());
			
			
		}
	}
	
	private void show(Player player) {
		
		
	}
	
	private void hide(Player player) {
		
		
	}
	
	/**
	 * 
	 * @param loc - Location of the player to whom the stats are shown
	 * @return The location made by direction of view and coordinates of the player
	 */
	
	public static Location getPosition(Location loc) {

		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		float yawZ = (float) (loc.getYaw() * Math.PI / (float) 180);
		float yawX = (float) (loc.getYaw() * (-1) * Math.PI / (float) 180);
		float pitch = (float) (loc.getPitch() * (-1) * Math.PI / (float) 180);

		return new Location(loc.getWorld(), x + STATS_DISTANCE * Math.sin(yawX), y + STATS_DISTANCE * Math.sin(pitch),
				z + STATS_DISTANCE * Math.cos(yawZ));
	}
}

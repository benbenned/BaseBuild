package de.ben.BaseBuild.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.World;

public class Hologram {

	private List<EntityArmorStand> entities;
	private Location loc;
	private World world;
	private double distance;
	private String[] text;
	private final static int STATS_DISTANCE = 2;
	private boolean isVisible = false;
	private Location currentLocation;
	
	/**
	 * 
	 * @param text - Set a maximum of 50 characters per string
	 * @param player - Player who is creating the hologram
	 * @param distance - Distance between the lines of the hologram
	 */
	
	public Hologram(String[] text, Player player, double distance) {
		
		loc = player.getLocation();
		world = ((CraftWorld) loc.getWorld()).getHandle();
		this.distance = distance;
		this.text = text;
		entities = new ArrayList<>();
		
		build();
	}
	
	private void build() {
		
		currentLocation = getStatsHologramLocation(loc);
		
		double x = currentLocation.getX();
		double y = currentLocation.getY();
		double z = currentLocation.getZ();
		
		for(String line : text) {
			
			EntityArmorStand stand = new EntityArmorStand(world, x, y, z);
			
			y -= this.distance;
			
			stand.setCustomNameVisible(true);
			stand.setCustomName(line);
			stand.setInvisible(true);
			stand.setGravity(false);
			
			entities.add(stand);
		}
	}
	
	public void show(Player player) {
		
		isVisible = true;
		
		for(EntityArmorStand current : entities) {
			
			PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(current);
			
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public void hide(Player player) {
		
		for(EntityArmorStand current : entities) {
			
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(current.getId());
			
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
		
		isVisible = false;
	}
	
	public static Location getStatsHologramLocation(Location loc) {
		
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		float yawZ = (float) (loc.getYaw() * Math.PI / (float) 180);
		float yawX = (float) (loc.getYaw() * (-1) * Math.PI / (float) 180);
		float pitch = (float) (loc.getPitch() * (-1) * Math.PI / (float) 180);
		
		return new Location(loc.getWorld(), x + STATS_DISTANCE * Math.sin(yawX), y + STATS_DISTANCE * Math.sin(pitch), z + STATS_DISTANCE * Math.cos(yawZ));
	}
	
	public void move(Location location) {
		
		for(EntityArmorStand current : entities) {
			
			current.enderTeleportTo(location.getX(), location.getY(), location.getZ());
				
			location.setY(location.getY() - distance);
		}
	}
	
	public void move2(Location location) {
		
		for(EntityArmorStand current : entities) {
			
			CraftLivingEntity e = (CraftLivingEntity) current.getBukkitEntity();
			
			e.setVelocity(new Vector(0.0, 8, 0.0));
			
			Vector v = e.getVelocity();
			
			System.out.println(v.getX() + " " + v.getY() + " " + v.getZ());
		}
	}
	
	public boolean isVisible() {
		return isVisible;
	}
}

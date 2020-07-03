package de.ben.BaseBuild.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.teams.Team;

public class LocationManager {

	private HashMap<Object, Location> locs;

	public LocationManager(Team[] teams) {

		locs = new HashMap<>();

		try {
			for (Team current : teams) {

				Location loc;

				loc = getLocationFromFile(current);
				locs.put(current, loc);
			}

			locs.put("SPEC", getLocationFromFile("SPEC"));
			locs.put("LOBBY", getLocationFromFile("LOBBY"));

		} catch (LocationNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Location getLocationFromFile(Object obj) throws LocationNotFoundException {

		if (obj instanceof Team) {

			Team team = (Team) obj;

			String locName = team.getLocName();
			String teamString = Main.getBf().getString("CORDS." + locName);

			String[] cords = teamString.split(";");

			World world = Bukkit.getWorld(cords[0]);
			double x = Double.parseDouble(cords[1]);
			double y = Double.parseDouble(cords[2]);
			double z = Double.parseDouble(cords[3]);
			float yaw = Float.parseFloat(cords[4]);
			float pitch = Float.parseFloat(cords[5]);

			Location loc = new Location(world, x, y, z, yaw, pitch);

			return loc;
		}
		if (obj instanceof String) {

			String locName = (String) obj;
			String locString = Main.getBf().getString("CORDS." + locName);

			String[] cords = locString.split(";");

			World world = Bukkit.getWorld(cords[0]);
			double x = Double.parseDouble(cords[1]);
			double y = Double.parseDouble(cords[2]);
			double z = Double.parseDouble(cords[3]);
			float yaw = Float.parseFloat(cords[4]);
			float pitch = Float.parseFloat(cords[5]);

			Location loc = new Location(world, x, y, z, yaw, pitch);

			return loc;
		}
		return null;
	}
	
	public static Location getLobbyLocation() {
		
		String locString = Main.getBf().getString("CORDS.LOBBY");
		
		String[] cords = locString.split(";");

		World world = Bukkit.getWorld(cords[0]);
		double x = Double.parseDouble(cords[1]);
		double y = Double.parseDouble(cords[2]);
		double z = Double.parseDouble(cords[3]);
		float yaw = Float.parseFloat(cords[4]);
		float pitch = Float.parseFloat(cords[5]);

		Location loc = new Location(world, x, y, z, yaw, pitch);

		return loc;
	}

	public Location getLocation(Object obj) {
		return locs.get(obj);
	}
	
	public static int getDistance(int a, int b) {
		
		if((a >= 0 && b >= 0) || (a <= 0 && b <= 0)) {
			
			return Math.abs(a - b);
		} else {
			
			return Math.abs(a) + Math.abs(b);
		}
	}

	public void teleportPlayerToTeam(Player player) {
		player.teleport(getLocation(Main.getTeamManager().getPlayersTeam(player)));
	}

	public void teleportAllTeams() {

		for (Team team : Team.values()) {

			Location loc = getLocation(team);

			for (Player current : team.getPlayers()) {

				current.teleport(loc);
			}
		}
	}
}

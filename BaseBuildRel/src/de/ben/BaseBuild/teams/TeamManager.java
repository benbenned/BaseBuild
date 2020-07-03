package de.ben.BaseBuild.teams;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mojang.authlib.GameProfile;

import de.ben.BaseBuild.gamestates.EndingState;
import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.stats.StatsManager;
import de.ben.BaseBuild.utils.PlayersMethods;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;

public class TeamManager {

	private HashMap<Player, Team> teamFinder;
	private HashMap<String, String> nameFinder;
	
	private Field nameField;
	private Team winning;

	public TeamManager() {

		teamFinder = new HashMap<>();
		nameFinder = new HashMap<>();
		
		nameField = PlayersMethods.getField(GameProfile.class, "name");
	}

	public void addPlayer(Player player, Team team) {

		teamFinder.put(player, team);

		team.getPlayers().add(player);
		team.setSize(team.getSize() + 1);

		player.setDisplayName(team.getChatColor() + player.getName());
		player.setPlayerListName(team.getChatColor() + player.getName());
		
		player.sendMessage(Main.PREFIX + "§aYou joined Team " + team.getChatColor() + team.getName().toUpperCase() + "§a!");
	}

	public void removePlayer(Player player) {

		Team team = teamFinder.get(player);

		teamFinder.remove(player);

		team.getPlayers().remove(player);
		team.setSize(team.getSize() - 1);

		player.setDisplayName(player.getName());
		player.setPlayerListName(player.getName());
	}

	public void setRandomTeam(Player player) {

		Team team = findRandomTeam();

		addPlayer(player, team);
	}

	private Team findRandomTeam() {

		boolean b = false;
		int teamIndex = 0;

		Random r = new Random();
		
		while (b == false) {

			teamIndex = r.nextInt(Team.values().length);

			if (!Team.values()[teamIndex].isFull()) {
				b = true;
			}
		}
		
		return Team.values()[teamIndex];
	}
	
	public void makeTeamsRight() {
		
		if(Team.values().length > 1) {
			
			Team empty = getEmptyTeam();
			Random r = new Random();
			
			float stealPlayersPerTeamF = (float) Main.getPlayers().size() / (float) Team.values().length / (float) getFilledTeams().size();
			int stealPlayersPerTeamI = (int) Math.round(stealPlayersPerTeamF);
			
			for(Team current : getTeamsGreaterThanSteal(stealPlayersPerTeamI)) {
				
				for(int i = 1; i <= stealPlayersPerTeamI; i++) {
					
					int playerIndex = r.nextInt(current.getSize());
					
					Player p = current.getPlayers().get(playerIndex);
					
					removePlayer(p);
					addPlayer(p, empty);
				}
			}
		}
	}
	
	private List<Team> getFilledTeams() {
		
		List<Team> teams = new ArrayList<>();
		
		for(Team current : Team.values()) {
			
			if(!current.isEmpty()) {
				teams.add(current);
			}
		}
		return teams;
	}
	
	private List<Team> getTeamsGreaterThanSteal(int stealPlayers) {
		
		List<Team> teams = new ArrayList<>();
		
		for(Team current : Team.values()) {
			
			if(current.getPlayers().size() > stealPlayers) {
				teams.add(current);
			}
		}
		return teams;
	}
	
	private Team getEmptyTeam() {
		
		for(Team current : Team.values()) {
			
			if(current.isEmpty()) {
				return current;
			}
		}
		return null;
	}
	
	public boolean areEnoughTeamsFilled() {
		
		int emptyTeams = 0;
		int maxEmptyTeams = Team.values().length - 2;
		
		for(Team current : Team.values()) {
			
			if(current.isEmpty()) {
				emptyTeams++;
			}
		}
		return emptyTeams <= maxEmptyTeams ? true : false;
	}
	
	public boolean isTeamAppendee(Player player) {
		
		boolean b = false;
		
		for(Team current : Team.values()) {
			
			if(current.getPlayers().contains(player)) {
				b = true;
				return b;
			}
		}
		return b;
	}
	
	public Team getPlayersTeam(Player player) {
		return teamFinder.containsKey(player) ? teamFinder.get(player) : null;
	}
	
	public String getPlayersRealName(Player player) {
		return nameFinder.containsKey(player.getName()) ? nameFinder.get(player.getName()) : null;
	}
	
	public void sendTeamMessage(String msg, Team team) {
		
		for(Player current : team.getPlayers()) {
			
			current.sendMessage(msg);
		}
	}
	
	public Inventory getTeamSelector() {
		
		Inventory inv = Bukkit.createInventory(null, 9 * 1, "§fChoose your team!");
		
		for(Team current : Team.values()) {
			
			ItemStack item = new ItemStack(Material.WOOL, 1, current.getBlockId());
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<>();
			
			meta.setDisplayName(current.getChatColor() + current.getName().toUpperCase() + " §8 - §7" + current.getSize() + "/" + Team.TEAM_SIZE_MAX);
			
			for(Player p : current.getPlayers()) {
				lore.add(current.getChatColor() + p.getName());
			}
			
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			inv.addItem(item);
		}
		return inv;
	}
	
	public int getPlayersSize() {
		return Main.getPlayers().size() - Main.getSpecs().size();
	}

	public List<Player> getTeamAppendees() {
		
		List<Player> players = new ArrayList<>();
		
		for(Team current : Team.values()) {
			
			for(Player p : current.getPlayers()) {
				
				players.add(p);
			}
		}
		return players;
	}
	
	public String getPlayersTeamName(Player player) {
		return isTeamAppendee(player) ? getPlayersTeam(player).getChatColor() + getPlayersTeam(player).getName() : "§fNone";
	}
	
	public void setTeamNameAboveHead(Player player) {
		
		CraftPlayer cp = (CraftPlayer) player;
		
		try {
			nameFinder.remove(player.getName());
			nameFinder.put(getPlayersTeam(player).getChatColor() + player.getName(), player.getName());
			
			nameField.set(cp.getProfile(), getPlayersTeam(player).getChatColor() + player.getName());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		PacketPlayOutEntityDestroy d = new PacketPlayOutEntityDestroy(cp.getEntityId());
		
		PlayersMethods.sendPacket(d);
		
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				
				PacketPlayOutNamedEntitySpawn ent = new PacketPlayOutNamedEntitySpawn(cp.getHandle());
				
				for(Player current : Main.getPlayers()) {
					
					if(!current.equals(player)) {
						
						((CraftPlayer) current).getHandle().playerConnection.sendPacket(ent);
					}
				}
			}
		}, 5L);
	}
	
	public void removeTeamNameAboveHead(Player player) {

		CraftPlayer cp = (CraftPlayer) player;

		try {
			String editName = cp.getName(); 
			
			nameField.set(cp.getProfile(), nameFinder.get(cp.getName()));
			
			nameFinder.remove(editName);
			nameFinder.put(cp.getName(), cp.getName());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		PacketPlayOutEntityDestroy d = new PacketPlayOutEntityDestroy(cp.getEntityId());

		PlayersMethods.sendPacket(d);

		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {

				PacketPlayOutNamedEntitySpawn ent = new PacketPlayOutNamedEntitySpawn(cp.getHandle());

				for (Player current : Main.getPlayers()) {

					if (!current.equals(player)) {

						((CraftPlayer) current).getHandle().playerConnection.sendPacket(ent);
					}
				}
			}
		}, 7L);
	}
	
	public void win(Team team) {
		
		Main.getGameStateManager().setGameState(new EndingState());
		
		Bukkit.broadcastMessage(Main.PREFIX + "§aTeam " + team.getChatColor() + team.getName().toUpperCase() + " §awon the game!");
		
		winning = team;
	}
	
	public HashMap<String, String> getNameFinder() {
		return nameFinder;
	}
	
	public Team getWinning() {
		return winning;
	}
}

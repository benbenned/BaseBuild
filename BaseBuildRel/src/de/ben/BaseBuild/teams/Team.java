package de.ben.BaseBuild.teams;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum Team {
	
	RED("Red", ChatColor.RED, (short) 14, "RED"),
	BLUE("Blue", ChatColor.BLUE, (short) 11, "BLUE");
	
	public static final int TEAM_SIZE_MAX = 5;
	
	private String name;
	private ChatColor chatColor;
	private short blockId;
	private String locName;
	private List<Player> players;
	private int size = 0;
	
	private Team(String name, ChatColor chatColor, short blockId, String locName) {
		
		this.name = name;
		this.chatColor = chatColor;
		this.blockId = blockId;
		this.locName = locName;
		
		players = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public short getBlockId() {
		return blockId;
	}
	
	public ChatColor getChatColor() {
		return chatColor;
	}
	
	public String getLocName() {
		return locName;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public boolean isFull() {
		return size >= TEAM_SIZE_MAX ? true : false;
	}
	
	public boolean isEmpty() {
		return size == 0 ? true : false;
	}
}

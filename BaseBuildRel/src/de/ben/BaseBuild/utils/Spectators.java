package de.ben.BaseBuild.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.ben.BaseBuild.main.Main;

public class Spectators {

	public boolean isSpectator(Player player) {
		return Main.getSpecs().contains(player) ? true : false;
	}
	
	public void sendSpecMessage(String msg) {
		
		for(Player current : Main.getSpecs()) {
			
			current.sendMessage(msg);
		}
	}
	
	public Inventory getSpectatorInventory() {
		
		int players = Main.getTeamManager().getPlayersSize();
		Inventory inv = Bukkit.createInventory(null, ((players + 8) / 9) * 9, "Teleport to player!");
		
		for(Player current : Main.getTeamManager().getTeamAppendees()) {
			
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			SkullMeta meta = (SkullMeta) skull.getItemMeta();
			
			meta.setOwner(current.getName());
			meta.setDisplayName(Main.getTeamManager().getPlayersTeam(current).getChatColor() + current.getName());
			
			skull.setItemMeta(meta);
			
			inv.addItem(skull);
		}
		return inv;
	}
}

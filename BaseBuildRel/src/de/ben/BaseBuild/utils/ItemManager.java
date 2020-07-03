package de.ben.BaseBuild.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.teams.Team;

public class ItemManager {
	
	public void setFightItems(Player player) {
		
		Team team = Main.getTeamManager().getPlayersTeam(player);
		
		ItemStack stick = new ItemStack(Material.STICK, 1);
		ItemStack bow = new ItemStack(Material.BOW, 1);
		ItemStack arrows = new ItemStack(Material.ARROW, 6);
		
		stick.addEnchantment(Enchantment.KNOCKBACK, 1);
		bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
		
		ItemMeta stickMeta = stick.getItemMeta();
		ItemMeta bowMeta = bow.getItemMeta();
		ItemMeta arrowsMeta = arrows.getItemMeta();
		
		stickMeta.setDisplayName(team.getChatColor() + "STICK");
		bowMeta.setDisplayName(team.getChatColor() + "BOW");
		arrowsMeta.setDisplayName(team.getChatColor() + "ARROW");
		
		stick.setItemMeta(stickMeta);
		bow.setItemMeta(bowMeta);
		arrows.setItemMeta(arrowsMeta);
		
		player.getInventory().setItem(0, stick);
		player.getInventory().setItem(1, bow);
		player.getInventory().setItem(8, arrows);
	}
	
	public void setBuildItems(Player player) {
		
		Team team = Main.getTeamManager().getPlayersTeam(player);
		
		ItemStack sandstone = new ItemStack(Material.WOOL, 75, team.getBlockId());
		ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		
		ItemMeta sandstoneMeta = sandstone.getItemMeta();
		ItemMeta pickaxeMeta = pickaxe.getItemMeta();
		
		sandstoneMeta.setDisplayName(team.getChatColor() + "BLOCKS");
		pickaxeMeta.setDisplayName(team.getChatColor() + "PICKAXE");
		
		sandstone.setItemMeta(sandstoneMeta);
		pickaxe.setItemMeta(pickaxeMeta);
		
		player.getInventory().setItem(0, pickaxe);
		player.getInventory().setItem(1, sandstone);
	}
	
	public void setEndingItems(Player player) {
		
		ItemStack glowstone = new ItemStack(Material.GLOWSTONE_DUST, 1);
		
		ItemMeta glowMeta = glowstone.getItemMeta();
		
		glowMeta.setDisplayName("§fLEAVE");
		
		glowstone.setItemMeta(glowMeta);
		
		player.getInventory().setItem(8, glowstone);
	}
	
	public void setSpectatorItems(Player player) {
		
		ItemStack star = new ItemStack(Material.NETHER_STAR, 1);
		ItemStack glowstone = new ItemStack(Material.GLOWSTONE_DUST, 1);
		
		ItemMeta starMeta = star.getItemMeta();
		ItemMeta glowMeta = glowstone.getItemMeta();
		
		starMeta.setDisplayName(ChatColor.AQUA + "TELEPORTER");
		glowMeta.setDisplayName(ChatColor.AQUA + "LEAVE");
		
		List<String> starLore = new ArrayList<>();
		
		for(Team current : Team.values()) {
			
			ChatColor color = current.getChatColor();
			
			for(Player p : current.getPlayers()) {
			
				starLore.add(color + p.getName());
			}
		}
		
		starMeta.setLore(starLore);
		
		star.setItemMeta(starMeta);
		glowstone.setItemMeta(glowMeta);
		
		player.getInventory().setItem(0, star);
		player.getInventory().setItem(8, glowstone);
	}
	
	public void setSelectorItems(Player player) {
		
		ItemStack bed = new ItemStack(Material.BED, 1);
		ItemStack glowstone = new ItemStack(Material.GLOWSTONE_DUST, 1);
		
		ItemMeta bedMeta = bed.getItemMeta();
		ItemMeta glowMeta = glowstone.getItemMeta();
		
		bedMeta.setDisplayName("§bCHOOSE YOUR TEAM!");
		glowMeta.setDisplayName(ChatColor.AQUA + "LEAVE");
		
		bed.setItemMeta(bedMeta);
		glowstone.setItemMeta(glowMeta);
		
		player.getInventory().setItem(0, bed);
		player.getInventory().setItem(8, glowstone);
	}
}

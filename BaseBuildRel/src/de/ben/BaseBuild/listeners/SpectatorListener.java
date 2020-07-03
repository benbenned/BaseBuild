package de.ben.BaseBuild.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.ben.BaseBuild.gamestates.BuildState;
import de.ben.BaseBuild.gamestates.FightState;
import de.ben.BaseBuild.main.Main;

public class SpectatorListener implements Listener {

	@EventHandler
	public void onItemClick(PlayerInteractEvent e) {

		if (Main.getGameStateManager().getCurrentGameState() instanceof BuildState
				|| Main.getGameStateManager().getCurrentGameState() instanceof FightState) {

			Player p = e.getPlayer();

			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

				if (e.getItem() != null) {

					if (e.getItem().getType() == Material.GLOWSTONE_DUST) {

						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kick " + p.getName());
						return;
					}

					if (e.getItem().getType() == Material.NETHER_STAR) {

						p.openInventory(Main.getSpectators().getSpectatorInventory());
						return;
					}
				}
			}
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {

		if (e.getWhoClicked() instanceof Player) {

			Player p = (Player) e.getWhoClicked();

			if (Main.getSpectators().isSpectator(p)) {

				if (e.getClickedInventory().getTitle().equals("Teleport to player!")) {

					if (e.getCurrentItem().getType() == Material.SKULL_ITEM
							|| e.getCurrentItem().getType() == Material.SKULL) {

						ItemStack skull = e.getCurrentItem();
						SkullMeta meta = (SkullMeta) skull.getItemMeta();

						try {
							p.teleport(Bukkit.getPlayer(meta.getOwner()));
							p.sendMessage(Main.PREFIX
									+ "§7You were teleported to " + Main.getTeamManager()
											.getPlayersTeam(Bukkit.getPlayer(meta.getOwner())).getChatColor()
									+ Bukkit.getPlayer(meta.getOwner()).getName() + "§7!");
							System.out.println("try");
						} catch (Exception ex) {
							p.sendMessage(Main.PREFIX + "§cPlayer is no longer ingame!");
							System.out.println("catch");
						}
						p.closeInventory();
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent e) {

		if (Main.getGameStateManager().getCurrentGameState() instanceof BuildState
				|| Main.getGameStateManager().getCurrentGameState() instanceof FightState) {

			if (e.getBlock().getType() == Material.SKULL || e.getBlock().getType() == Material.SKULL_ITEM) {

				e.setCancelled(true);
			}
		}
	}
}

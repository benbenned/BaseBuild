package de.ben.BaseBuild.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import de.ben.BaseBuild.gamestates.EndingState;
import de.ben.BaseBuild.gamestates.LobbyState;
import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.stats.StatsManager;
import de.ben.BaseBuild.teams.Team;
import de.ben.BaseBuild.utils.Hologram;

public class LobbyListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {

		if (Main.getGameStateManager().getCurrentGameState() instanceof LobbyState) {

			Player p = e.getPlayer();

			if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {

				if (e.getItem() != null) {

					if (e.getItem().getType() == Material.GLOWSTONE_DUST) {

						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kick " + p.getName());
						return;
					}

					if (e.getItem().getType() == Material.BED || e.getItem().getType() == Material.BED_BLOCK) {

						p.openInventory(Main.getTeamManager().getTeamSelector());
						return;

					}
				}
			}
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {

		if (Main.getGameStateManager().getCurrentGameState() instanceof LobbyState
				|| Main.getGameStateManager().getCurrentGameState() instanceof EndingState) {

			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventory(InventoryClickEvent e) {

		if (Main.getGameStateManager().getCurrentGameState() instanceof LobbyState) {

			if (e.getWhoClicked() instanceof Player) {

				Player p = (Player) e.getWhoClicked();

				if (e.getClickedInventory().getTitle().equals("§fChoose your team!")) {

					if (e.getCurrentItem().getType() == Material.WOOL) {

						Team team = Team.values()[e.getSlot()];

						if (Main.getTeamManager().isTeamAppendee(p)) {

							Main.getTeamManager().removePlayer(p);
						}
						Main.getTeamManager().addPlayer(p, team);

						p.closeInventory();
					}
				}
				
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent e) {

		if (Main.getGameStateManager().getCurrentGameState() instanceof LobbyState) {

			if (e.getBlock().getType() == Material.BED_BLOCK || e.getBlock().getType() == Material.BED) {

				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {

		if (Main.getGameStateManager().getCurrentGameState() instanceof LobbyState
				|| Main.getGameStateManager().getCurrentGameState() instanceof EndingState) {

			StatsManager sm = Main.getStatsManager();
			Player p = e.getPlayer();

			if (e.isSneaking()) {

				Hologram h = null;

				String[] fileStats = sm.getStatsToShow(p.getName());

				String[] stats = { "§7Statistics of §6" + p.getName() + " §8(All time)", "§7Points: §6" + fileStats[0],
						"§7Played: §6" + fileStats[1], "§7Won: §6" + fileStats[2], "§7Kills §6" + fileStats[3],
						"§7Deaths: §6" + fileStats[4],
						"§7K/D: §6" + sm.getKD(Integer.parseInt(fileStats[3]), Integer.parseInt(fileStats[4])),
						"§7W/P: §6" + sm.getWinRate(Integer.parseInt(fileStats[1]), Integer.parseInt(fileStats[2])) };

				if (!sm.getHolograms().containsKey(p)) {

					h = new Hologram(stats, p, 0.25);
					sm.getHolograms().put(p, h);

					System.out.println("NEW HOLOGRAM");
				}

				h = sm.getHolograms().get(p);

				h.move(Hologram.getStatsHologramLocation(p.getLocation()));
				h.show(p);
				return;
			}

			if (!e.isSneaking()) {

				Hologram h = sm.getHolograms().get(p);

				if (h == null) {
					return;
				}

				h.hide(p);
				return;
			}
		}
	}
}

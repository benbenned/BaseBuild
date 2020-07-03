package de.ben.BaseBuild.listeners;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.ben.BaseBuild.main.Main;
import de.ben.BaseBuild.utils.PlayersMethods;

public class PlayersJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		HashMap<String, String> uuids = Main.getUuids();
		HashMap<String, String> names = Main.getNames();
		
		Player p = e.getPlayer();

		String nameLow = p.getName().toLowerCase();
		String name = p.getName();
		String uuid = p.getUniqueId().toString();

		if (!uuids.containsKey(name) && !uuids.containsValue(uuid)) {

			names.put(nameLow, name);
			uuids.put(name, uuid);

			System.out.println("check size: " + names.size());
			System.out.println("names: " + names.toString());

			return;
		}

		else if (uuids.containsKey(name) && !uuids.get(name).equals(uuid)) {

			uuids.replace(name, uuid);

			return;

		} else if (uuids.containsValue(uuid) && !PlayersMethods.tryGetKey(uuids, uuid).equals(name)) {

			String oldName = PlayersMethods.tryGetKey(uuids, uuid);

			names.remove(oldName.toLowerCase());
			names.put(nameLow, name);

			uuids.remove(oldName);
			uuids.put(name, uuid);

			return;
		} else {
			return;
		}
	}
}

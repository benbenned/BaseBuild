package de.ben.BaseBuild.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ben.BaseBuild.main.Main;

public class SetCordsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to execute this command.");
			return true;
		}

		Player p = (Player) sender;

		if (!p.hasPermission("BASEBUILD_CORDS")) {
			p.sendMessage(Main.PREFIX + "§cYou don't have the permission to execute this command.");
			return true;
		}

		if (args.length != 1) {
			p.sendMessage("§cUsage: /setcords [Team]");
			return true;
		}

		if (args.length == 1) {
			
			String name = args[0];
			
			String world = p.getWorld().getName();
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			double yaw = p.getLocation().getYaw();
			double pitch = p.getLocation().getPitch();
			
			String location = world + ";" + x + ";" + y + ";" + z + ";" + yaw + ";" + pitch;
			
			Main.getBf().setValue("CORDS." + name, location);
			Main.getBf().save();
			
			p.sendMessage(Main.PREFIX + "§7You §asuccessfully §7set the location of the spawn of §6" + name + "§7!");
			return true;
		}
		return true;
	}

}

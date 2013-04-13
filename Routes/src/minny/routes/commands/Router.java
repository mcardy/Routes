package minny.routes.commands;

import minny.routes.Routes;
import minny.routes.RoutesCommand;
import minny.routes.utils.Config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Router extends RoutesCommand {

	Config config;
	Routes plugin;

	public Router(Routes plugin) {
		config = new Config(plugin, "positions.yml");
		this.plugin = plugin;
	}

	public void setNext(CommandSender sender) {
		int current = config.getConfig().getInt("nextint");
		Player player = (Player) sender;
		Location pos = player.getLocation();
		String route = String.valueOf(current++);

		config.getConfig().set(route + "x", pos.getX());
		config.getConfig().set(route + "y", pos.getY());
		config.getConfig().set(route + "z", pos.getZ());
		config.getConfig().set(route + "yaw", pos.getYaw());
		config.getConfig().set(route + "pitch", pos.getPitch());
		config.getConfig().set(route + "world", player.getWorld().toString());

		config.getConfig().set("nextint", current++);

		config.saveConfig();

		sender.sendMessage(ChatColor.DARK_GREEN + "Route " + current++
				+ " has been set");

	}

	public void set(CommandSender sender, String args) {
		if (isInt(args)) {
			Player player = (Player) sender;
			Location pos = player.getLocation();
			String route = String.valueOf(args) + ".";

			config.getConfig().set(route + "x", pos.getX());
			config.getConfig().set(route + "y", pos.getY());
			config.getConfig().set(route + "z", pos.getZ());
			config.getConfig().set(route + "yaw", pos.getYaw());
			config.getConfig().set(route + "pitch", pos.getPitch());
			config.getConfig().set(route + "world",
					player.getWorld().toString());

			config.saveConfig();

			sender.sendMessage(ChatColor.DARK_GREEN + "Route " + args
					+ " has been set");
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "Route must be a number");
		}
	}

	public void remove(CommandSender sender, String args) {
		if (isInt(args)) {
			config.getConfig().set(args, null);
			config.saveConfig();
			sender.sendMessage(ChatColor.DARK_PURPLE + "Route " + args
					+ " has been removed");
		}
	}

	public void pointTo(CommandSender sender, String args) {
		if (isInt(args)) {
			Player player = (Player) sender;
			String route = args + ".";
			int locX = config.getConfig().getInt(route + "x");
			int locY = config.getConfig().getInt(route + "y");
			int locZ = config.getConfig().getInt(route + "z");
			long locYaw = config.getConfig().getLong(route + "yaw");
			long locPitch = config.getConfig().getLong(route + "pitch");
			World locWorld = Bukkit.getWorld(config.getConfig().getString(
					route + "world"));

			Location point = new Location(locWorld, locX, locY, locZ, locYaw,
					locPitch);

			player.setCompassTarget(point);
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "Route must be a number");
		}
	}

	public int getCurrentPoint(CommandSender sender) {
		int point = plugin.currentPoint.get(sender.getName());
		if (!isInt(point)) {
			return 0;
		} else {
			return point;
		}
	}
	
	public void nextRoute(CommandSender sender){
		int currentPoint = getCurrentPoint(sender);
		if (config.getConfig().contains(currentPoint + ".x")){
			pointTo(sender, String.valueOf(currentPoint));
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "You have reached the end of your journey...");
		}
	}
	
}

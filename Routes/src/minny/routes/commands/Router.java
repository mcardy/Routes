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
		int current = config.getConfig().getInt("current");
		Player player = (Player) sender;
		Location pos = player.getLocation();
		String route = String.valueOf(current++) + ".";

		config.getConfig().set(route + "x", pos.getX());
		config.getConfig().set(route + "y", pos.getY());
		config.getConfig().set(route + "z", pos.getZ());
		config.getConfig().set(route + "world", player.getWorld().toString());

		config.getConfig().set("current", current++);

		config.saveConfig();

		sender.sendMessage(ChatColor.DARK_GREEN + "Route " + current--
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
			World locWorld = Bukkit.getWorld(config.getConfig().getString(
					route + "world"));

			Location point = new Location(locWorld, locX, locY, locZ, 0, 0);

			player.setCompassTarget(point);
			plugin.currentPoint.put(sender.getName(), args);
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "Route must be a number");
		}
	}

	public int getCurrentPoint(CommandSender sender) {
		if (plugin.currentPoint.containsKey(sender.getName())) {
			Object point = plugin.currentPoint.get(sender.getName());
			if (!isInt(point)) {
				return 0;
			} else {
				return Integer.valueOf((String) point);
			}
		} else {
			plugin.currentPoint.put(sender.getName(), 0);
			return 0;
		}
	}

	public void nextRoute(CommandSender sender) {
		int currentPoint = getCurrentPoint(sender);
		int nextPoint = currentPoint++;
		if (isRoute(nextPoint)) {
			pointTo(sender, String.valueOf(nextPoint));
			sender.sendMessage(ChatColor.getByChar(plugin.getConfig()
					.getString("Route-Next-Colour"))
					+ plugin.getConfig().getString("Route-Next"));
		} else {
			sender.sendMessage(ChatColor.getByChar(plugin.getConfig()
					.getString("Route-End-Colour"))
					+ plugin.getConfig().getString("Route-End"));
		}
	}

	public void backRoute(CommandSender sender) {
		int currentPoint = getCurrentPoint(sender);
		int backPoint = currentPoint--;
		if (isRoute(backPoint)) {
			pointTo(sender, String.valueOf(backPoint));
		} else {
			sender.sendMessage(ChatColor.DARK_RED
					+ "Something went wrong... Unable to go back...");
		}
	}

	public boolean isRoute(String args) {
		if (config.getConfig().contains(args + ".x")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRoute(int args) {
		if (config.getConfig().contains(args + ".x")) {
			return true;
		} else {
			return false;
		}
	}

	public void setDesc(CommandSender sender, String desc, String args) {
		if (isRoute(args)) {
			config.getConfig().set(args + ".desc", desc);
		} else {
			sender.sendMessage(ChatColor.DARK_RED
					+ "That route does not exist.");
		}
	}

	public void sendDesc(CommandSender sender, String args) {
		if (isRoute(args)) {
			String desc = config.getConfig().getString(args + ".desc");
			sender.sendMessage(ChatColor.DARK_AQUA + desc);
		} else {
			sender.sendMessage(ChatColor.DARK_RED
					+ "That route does not exist.");
		}
	}

	public void sendDesc(CommandSender sender, int args) {
		if (isRoute(args)) {
			String desc = config.getConfig().getString(args + ".desc");
			sender.sendMessage(ChatColor.DARK_AQUA + desc);
		} else {
			sender.sendMessage(ChatColor.DARK_RED
					+ "That route does not exist.");
		}
	}

	public boolean getAutoRoute(CommandSender sender) {
		if (plugin.autoRoute != null
				&& plugin.autoRoute.contains(sender.getName())) {
			return true;
		} else {
			return false;
		}
	}
}

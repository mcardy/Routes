package minny.routes.commands;

import minny.routes.Routes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRoute extends Router implements CommandExecutor {

	public CommandRoute(Routes plugin) {
		super(plugin);
	}

	String startRoute = plugin.getConfig().getString("AutoRoute-Start");
	String startRouteColour = plugin.getConfig().getString(
			"AutoRoute-Start-Colour");

	String endRoute = plugin.getConfig().getString("AutoRoute-End");
	String endRouteColour = plugin.getConfig()
			.getString("AutoRoute-End-Colour");

	String end = plugin.getConfig().getString("Route-End");
	String endColour = plugin.getConfig().getString("Route-End-Colour");

	String next = plugin.getConfig().getString("Route-Next");
	String nextColour = plugin.getConfig().getString("Route-Next-Colour");

	String back = plugin.getConfig().getString("Route-Back");
	String backColour = plugin.getConfig().getString("Route-Back-Colour");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				if (plugin.getConfig().getBoolean("Enable-Auto-Route")) {
					if (sender.hasPermission("routes.autoroute")) {
						if (plugin.autoRoute != null
								&& !plugin.autoRoute.contains(sender.getName())) {
							if (isRoute(0)) {
								plugin.autoRoute.add(sender.getName());
								plugin.currentPoint.put(sender.getName(), 0);
								sender.sendMessage(ChatColor
										.getByChar(startRouteColour)
										+ startRoute);
								startRoute(sender);
							} else {
								sender.sendMessage(ChatColor.DARK_RED + "No routes set...");
							}

						} else {
							plugin.autoRoute.remove(sender.getName());
							sender.sendMessage(ChatColor
									.getByChar(endRouteColour) + endRoute);

						}
					}
				} else {
					sender.sendMessage(ChatColor.RED
							+ "Do '/help routes' for the list of routes commands");
				}
			} else {
				if (args[0].equalsIgnoreCase("next")) {
					if (hasPerm(sender, "routes.next")) {
						nextRoute(sender);
					} else {
						needOP(sender);
					}
				} else if (args[0].equalsIgnoreCase("back")) {
					if (hasPerm(sender, "routes.back")) {
						backRoute(sender);
						sender.sendMessage(ChatColor.getByChar(backColour)
								+ back);
					} else {
						needOP(sender);
					}
				} 
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}
}

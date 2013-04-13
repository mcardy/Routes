package minny.routes.commands;

import minny.routes.Routes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRoute extends Router implements CommandExecutor {

	private CommandSet set;
	private CommandDel del;
	
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

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				if (plugin.getConfig().getBoolean("EnableAutoRoute")) {
					if (sender.hasPermission("routes.autoroute")) {
						if (!plugin.autoRoute.contains(sender.getName())) {
							plugin.autoRoute.add(sender.getName());
							nextRoute(sender);
							sender.sendMessage(ChatColor
									.getByChar(startRouteColour) + startRoute);
						} else {
							plugin.autoRoute.remove(sender.getName());
							sender.sendMessage(ChatColor
									.getByChar(endRouteColour) + endRoute);
						}
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Do '/help routes' for the list of routes commands");
				}
			} else {
				if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("setnext")){
					set.commandSet(sender, args);
				} else if (args[0].equalsIgnoreCase("del")){
					del.commandDel(sender, args);
				}
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}

}

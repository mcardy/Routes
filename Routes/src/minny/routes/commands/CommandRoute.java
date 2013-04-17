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
					sender.sendMessage(ChatColor.RED
							+ "Do '/help routes' for the list of routes commands");
				}
			} else {
				if (args[0].equalsIgnoreCase("next")) {
					if (hasPerm(sender, "routes.next")) {
						nextRoute(sender);
						sender.sendMessage(ChatColor.getByChar(nextColour)
								+ next);
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
				} else if (args[0].equalsIgnoreCase("set")
						|| args[0].equalsIgnoreCase("setnext")) {
					if (hasPerm(sender, "route.set")) {
						if (args.length == 1) {
							if (args[0].equalsIgnoreCase("set")) {
								notEnough(sender);
							} else if (args[0].equalsIgnoreCase("setnext")) {
								sender.sendMessage("Test");
								setNext(sender);
							}
						} else if (args.length == 2) {
							if (args[0].equalsIgnoreCase("set")) {
								set(sender, args[1]);
							} else if (args[0].equalsIgnoreCase("setnext")) {
								setNext(sender);
							}
						}
					} else {
						needOP(sender);
					}
				} else if (args[0].equalsIgnoreCase("del")) {
					if (hasPerm(sender, "routes.del")) {
						if (args[0].equalsIgnoreCase("del")) {
							if (args.length == 1) {
								notEnough(sender);
							} else {
								remove(sender, args[1]);
							}
						}
					} else {
						needOP(sender);
					}
				} else if (args[0].equalsIgnoreCase("setdesc")) {
					if (hasPerm(sender, "routes.setdesc")) {
						if (args.length > 2) {
							StringBuffer result = new StringBuffer();
							for (int i = 0; i < args.length; i++) {
								if (!args[i].equals(args[0])
										&& !args[i].equals(args[1])) {
									result.append(args[i]);
									result.append(" ");
								}

							}
							String desc = result.toString();
							setDesc(sender, desc, args[1]);

						} else {
							notEnough(sender);
						}
					} else {
						needOP(sender);
					}
				} else if (args[0].equalsIgnoreCase("desc")) {
					if (hasPerm(sender, "routes.desc")) {
						if (args.length > 2) {
							sendDesc(sender, getCurrentPoint(sender));
						} else {
							sendDesc(sender, args[1]);
						}
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

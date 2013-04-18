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

	String startRoute = plugin.getConfig()
			.getString("Messages.AutoRoute.Start");
	String startRouteColour = plugin.getConfig().getString(
			"Messages.AutoRoute.Start-Colour");

	String endRoute = plugin.getConfig().getString("Messages.AutoRoute.End");
	String endRouteColour = plugin.getConfig().getString(
			"Messages.AutoRoute.End-Colour");

	String end = plugin.getConfig().getString("Messages.Route-End");
	String endColour = plugin.getConfig()
			.getString("Messages.Route-End-Colour");

	String next = plugin.getConfig().getString("Messages.Route-Next");
	String nextColour = plugin.getConfig().getString(
			"Messages.Route-Next-Colour");

	String back = plugin.getConfig().getString("Messages.Route-Back");
	String backColour = plugin.getConfig().getString(
			"Messages.Route-Back-Colour");

	String delay = plugin.getConfig().getString("Messages.Delay");
	String delayColour = plugin.getConfig().getString("Messages.Delay-Colour");

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
								sender.sendMessage(ChatColor.DARK_RED
										+ "No routes set...");
							}

						} else {
							plugin.autoRoute.remove(sender.getName());
							sender.sendMessage(ChatColor
									.getByChar(endRouteColour) + endRoute);

						}
					} else {
						sender.sendMessage(ChatColor.RED
								+ "Do '/help routes' for the list of routes commands");
					}
				} else {
					sender.sendMessage(ChatColor.RED
							+ "Do '/help routes' for the list of routes commands");
				}
			} else {
				if (args[0].equalsIgnoreCase("next")) {
					if (hasPerm(sender, "routes.next")) {
						if (!plugin.commandDelay.contains(sender.getName())) {
							if (Integer.valueOf(String
									.valueOf(plugin.currentPoint.get(sender
											.getName()))) == 0) {
								if (plugin.getConfig().getBoolean(
										"Enable-Command-Delay")
										&& !hasPerm(sender,
												"routes.ignoredelay")) {
									plugin.commandDelay.add(sender.getName());
									int delay = plugin.getConfig().getInt(
											"Command-Delay");
									new RouteDelay(plugin, sender.getName())
											.runTaskLater(plugin, delay * 20);
								}
								startRoute(sender);
							} else {
								if (plugin.getConfig().getBoolean(
										"Enable-Command-Delay")
										&& !hasPerm(sender,
												"routes.ignoredelay")) {
									plugin.commandDelay.add(sender.getName());
									int delay = plugin.getConfig().getInt(
											"Command-Delay");
									new RouteDelay(plugin, sender.getName())
											.runTaskLater(plugin, delay * 20);
								}
								nextRoute(sender);
							}
						} else {
							sender.sendMessage(ChatColor.getByChar(delayColour)
									+ delay);
						}
					} else {
						needOP(sender);
					}
				} else if (args[0].equalsIgnoreCase("back")) {
					if (hasPerm(sender, "routes.back")) {
						if (!plugin.commandDelay.contains(sender.getName())) {
							if (plugin.getConfig().getBoolean(
									"Enable-Command-Delay")
									&& !hasPerm(sender, "routes.ignoredelay")) {
								plugin.commandDelay.add(sender.getName());
								int delay = plugin.getConfig().getInt(
										"Command-Delay");
								new RouteDelay(plugin, sender.getName())
										.runTaskLater(plugin, delay * 20);
							}
							backRoute(sender);
							sender.sendMessage(ChatColor.getByChar(backColour)
									+ back);
						} else {
							sender.sendMessage(ChatColor.getByChar(delayColour)
									+ delay);
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
				} else if (args[0].equalsIgnoreCase("info")) {
					if (hasPerm(sender, "routes.info")) {
						if (args.length == 1) {
							Object current = plugin.currentPoint.get(sender
									.getName());
							sendName(sender, String.valueOf(current));
							sendDesc(sender, String.valueOf(current));
						} else {
							sendName(sender, args[1]);
							sendName(sender, args[1]);
						}
					}
				} else if (args[0].equalsIgnoreCase("setname")) {
					if (hasPerm(sender, "routes.setname")) {
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
							setName(sender, desc, args[1]);

						} else {
							notEnough(sender);
						}
					} else {
						needOP(sender);
					}
				} else if (args[0].equalsIgnoreCase("name")) {
					if (hasPerm(sender, "routes.name")) {
						if (args.length > 2) {
							sendName(sender, getCurrentPoint(sender));
						} else {
							sendName(sender, args[1]);
						}
					} else {
						needOP(sender);
					}
				} else if (args[0].equalsIgnoreCase("set")
						|| args[0].equalsIgnoreCase("setnext")) {
					if (hasPerm(sender, "routes.set")) {
						if (args.length == 1) {
							if (args[0].equalsIgnoreCase("set")) {
								notEnough(sender);
							} else if (args[0].equalsIgnoreCase("setnext")) {
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
				} else if (args[0].equalsIgnoreCase("locate")) {
					if (hasPerm(sender, "routes.pointto")) {
						if (!plugin.commandDelay.contains(sender.getName())) {
							if (args.length == 1) {
								notEnough(sender);
							} else {
								pointTo(sender, args[1]);
								if (isRoute(args[1])) {
									sender.sendMessage(ChatColor.DARK_GREEN
											+ "Pointing to " + args[1]);
									if (plugin.getConfig().getBoolean(
											"Enable-Command-Delay")
											&& !hasPerm(sender,
													"routes.ignoredelay")) {
										plugin.commandDelay.add(sender
												.getName());
										int delay = plugin.getConfig().getInt(
												"Command-Delay");
										new RouteDelay(plugin, sender.getName())
												.runTaskLater(plugin,
														delay * 20);
									}
								}
							}
						} else {
							sender.sendMessage(ChatColor.getByChar(delayColour)
									+ delay);
						}
					} else {
						needOP(sender);
					}
				} else if (args[0].equalsIgnoreCase("setradius")) {
					if (hasPerm(sender, "routes.setradius")) {
						if (args.length == 1) {
							notEnough(sender);
						} else {
							if (isInt(args[1])) {
								setRadius(sender, args[0],
										Integer.parseInt(args[1]));
							} else {
								sender.sendMessage(ChatColor.DARK_RED
										+ "Route must be a number!");
							}
						}
					} else {
						needOP(sender);
					}
				} else if (args[0].equalsIgnoreCase("help")) {
					if (hasPerm(sender, "routes.help")) {
						sender.sendMessage(ChatColor.GREEN + "Routes commands:");
						sendHelp(sender);
					}
				} else {
					sender.sendMessage(ChatColor.RED
							+ "Unknown command. For help use /routes help");
				}
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}
}

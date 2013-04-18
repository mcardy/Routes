package minny.routes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import minny.routes.Routes;

public class CommandName extends Router implements CommandExecutor {

	public CommandName(Routes plugin) {
		super(plugin);
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender instanceof Player) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("setname")) {
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
				}
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}

}

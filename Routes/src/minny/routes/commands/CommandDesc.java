package minny.routes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import minny.routes.Routes;

public class CommandDesc extends Router implements CommandExecutor {

	public CommandDesc(Routes plugin) {
		super(plugin);
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender instanceof Player) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("setdesc")) {
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

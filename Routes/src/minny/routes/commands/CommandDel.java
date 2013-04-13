package minny.routes.commands;

import org.bukkit.command.CommandSender;

import minny.routes.Routes;

public class CommandDel extends Router {

	public CommandDel(Routes plugin) {
		super(plugin);
	}

	public void commandDel(CommandSender sender, String[] args) {
		if (hasPerm(sender, "routes.del")) {
			if (args[0].equalsIgnoreCase("del")) {
				if (args.length == 1) {
					notEnough(sender);
				} else {
					remove(sender, args[1]);
				}
			}
		}
	}

}

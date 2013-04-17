package minny.routes.commands;

import org.bukkit.command.CommandSender;

import minny.routes.Routes;

public class CommandSet extends Router {

	public CommandSet(Routes plugin) {
		super(plugin);
	}

	public void commandSet(CommandSender sender, String[] args) {
		if (sender.hasPermission("route.set")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("set")) {
					notEnough(sender);
				} else if (args[0].equalsIgnoreCase("setnext")) {
					sender.sendMessage("Test");
					setNext(sender);
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("set")){
					set(sender, args[1]);
				} else if (args[0].equalsIgnoreCase("setnext")) {
					setNext(sender);
				}
			}
		}
	}
}

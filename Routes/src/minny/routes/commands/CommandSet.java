package minny.routes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import minny.routes.Routes;

public class CommandSet extends Router implements CommandExecutor{

	public CommandSet(Routes plugin) {
		super(plugin);
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender instanceof Player){
			if (args[0].equalsIgnoreCase("set")
					|| args[0].equalsIgnoreCase("setnext")) {
				if (hasPerm(sender, "route.set")) {
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
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}
	

}

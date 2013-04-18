package minny.routes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import minny.routes.Routes;

public class CommandDel extends Router implements CommandExecutor {

	public CommandDel(Routes plugin) {
		super(plugin);
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender instanceof Player) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("del")) {
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
				}
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}

}

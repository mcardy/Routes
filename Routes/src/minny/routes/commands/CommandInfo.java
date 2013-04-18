package minny.routes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import minny.routes.Routes;

public class CommandInfo extends Router implements CommandExecutor {

	public CommandInfo(Routes plugin) {
		super(plugin);
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender instanceof Player) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("info")) {
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
			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}

}

package minny.routes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RoutesCommand {

	public static int getInt(String string) {
		if (isInt(string)) {
			return Integer.parseInt(string);
		} else {
			return 0;
		}
	}

	public static boolean isInt(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException nFE) {
			return false;
		}
		return true;
	}

	public static boolean isInt(Object string) {
		try {
			String s = (String) string;
			Integer.parseInt(s);
		} catch (NumberFormatException nFE) {
			return false;
		}
		return true;
	}

	public static boolean isInt(int i) {
		try {
			Integer.valueOf(i);
		} catch (NumberFormatException nFE) {
			return false;
		}
		return true;
	}

	public static boolean isOnline(String player) {
		Player target = (Bukkit.getServer().getPlayer(player));
		if (target == null) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isFloat(String string) {
		try {
			Float.parseFloat(string);
		} catch (NumberFormatException nFE) {
			return false;
		}
		return true;
	}

	public static boolean isItem(ItemStack i) {
		if (i.getTypeId() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean stringNullCheck(String string) {
		if (string != null) {
			return true;
		}
		return false;
	}

	public static boolean hasPerm(CommandSender sender, String string) {
		if (sender.hasPermission(string)) {
			return true;
		} else {
			return false;
		}
	}

	public void inGameOnly(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Must be an ingame player!");
	}

	public void tooMany(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Too many arguments");
	}

	public void notEnough(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Too few arguments");
	}

}

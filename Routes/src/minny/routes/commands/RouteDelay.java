package minny.routes.commands;

import minny.routes.Routes;

import org.bukkit.scheduler.BukkitRunnable;

public class RouteDelay extends BukkitRunnable {

	Routes plugin;
	String name;
	
	public RouteDelay(Routes plugin, String name){
		this.plugin = plugin;
		this.name = name;
	}
	
	@Override
	public void run() {
		plugin.commandDelay.remove(name);
	}

}

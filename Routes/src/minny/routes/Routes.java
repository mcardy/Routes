package minny.routes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import minny.routes.commands.CommandRoute;
import minny.routes.utils.Config;
import minny.routes.utils.UpdateChecker;

import org.bukkit.plugin.java.JavaPlugin;

public class Routes extends JavaPlugin{

	Routes plugin;
	UpdateChecker updateChecker;
	Config posConfig;
	
	public boolean isUpdate;
	public Map<String, Integer> currentPoint = new HashMap<String, Integer>();
	public Set<String> autoRoute = new HashSet<String>();

	public void onEnable(){
		updateChecker = new UpdateChecker(this);
		updateChecker.checkUpdate();
		
		loadConfig();
		loadCommands();
	}
	
	public void onDisable(){
		saveMap();
	}

	public void loadCommands(){
		plugin.getCommand("Route").setExecutor(new CommandRoute(plugin));		
	}
	
	public void loadConfig(){
		posConfig = new Config(this, "positions.yml");
		posConfig.saveDefaultConfig();
		
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}
	
	public void saveMap(){
		posConfig = new Config(this, "positions.yml");
		posConfig.getConfig().set("PointMap", currentPoint);
	}
}

package minny.routes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minny.routes.commands.CommandRoute;
import minny.routes.listeners.RoutesPlayerListener;
import minny.routes.utils.Config;
import minny.routes.utils.UpdateChecker;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Routes extends JavaPlugin {

	Routes plugin;
	UpdateChecker updateChecker;
	Config posConfig;
	Config playerConfig;

	public boolean isUpdate;
	public Map<String, Object> currentPoint;
	public List<String> autoRoute;
	public List<String> commandDelay = new ArrayList<String>();

	public void onEnable() {
		updateChecker = new UpdateChecker(this);
		updateChecker.checkUpdate();

		loadConfig();
		loadCommands();

		loadMaps();

		RoutesPlayerListener playerListener = new RoutesPlayerListener(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerListener, this);
	}

	public void onDisable() {
		saveMaps();
	}

	public void loadCommands() {
		getCommand("Route").setExecutor(new CommandRoute(this));
	}

	public void loadConfig() {
		posConfig = new Config(this, "positions.yml");
		posConfig.saveDefaultConfig();

		playerConfig = new Config(this, "playerdata.yml");
		playerConfig.saveDefaultConfig();

		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}

	public void saveMaps() {
		playerConfig = new Config(this, "playerdata.yml");
		playerConfig.getConfig().set("current-point", null);
		playerConfig.getConfig().createSection("current-point", currentPoint);
		playerConfig.getConfig().set("auto-route", autoRoute);
		playerConfig.saveConfig();
	}

	public void loadMaps() {
		playerConfig = new Config(this, "playerdata.yml");
		if (!playerConfig.getConfig().contains("current-point")
				|| playerConfig.getConfig().getString("current-point")
						.equalsIgnoreCase("{}")) {
			currentPoint = new HashMap<String, Object>();
		} else {
			currentPoint = playerConfig.getConfig()
					.getConfigurationSection("current-point").getValues(false);
		}
		if (!playerConfig.getConfig().contains("auto-route")
				|| playerConfig.getConfig().getString("auto-route")
						.equalsIgnoreCase("[]")) {
			autoRoute = new ArrayList<String>();
		} else {
			autoRoute = playerConfig.getConfig().getStringList("auto-route");
		}
	}
}

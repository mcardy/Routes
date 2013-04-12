package minny.routes;

import org.bukkit.plugin.java.JavaPlugin;

public class Routes extends JavaPlugin{

	public static Routes plugin;

	private RoutesUpdateChecker updateChecker;
	
	public boolean isUpdate;

	public void onEnable(){
		updateChecker = new RoutesUpdateChecker(this);
		updateChecker.checkUpdate();
		
		loadConfig();
		loadCommands();
	}
	
	public void onDisable(){
		
	}

	public void loadCommands(){
		
	}
	
	public void loadConfig(){
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}
	
}

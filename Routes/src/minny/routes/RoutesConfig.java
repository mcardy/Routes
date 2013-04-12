package minny.routes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class RoutesConfig {

	private final String fileName;
	private final JavaPlugin plugin;

	private File configFile;
	private FileConfiguration fileConfiguration;

	public RoutesConfig(JavaPlugin plugin, String fileName) {
		this.plugin = plugin;
		this.fileName = fileName;
	}

	public void reloadConfig() {
		if (configFile == null) {
			File dataFolder = plugin.getDataFolder();
			if (dataFolder == null)
				throw new IllegalStateException();
			configFile = new File(dataFolder, fileName);
		}
		fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

		InputStream defConfigStream = plugin.getResource(fileName);
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			fileConfiguration.setDefaults(defConfig);
		}
	}

	public FileConfiguration getConfig() {
		if (fileConfiguration == null) {
			this.reloadConfig();
		}
		return fileConfiguration;
	}

	public void saveConfig() {
		File dataFolder = plugin.getDataFolder();
		if (dataFolder == null)
			throw new IllegalStateException();
		configFile = new File(dataFolder, fileName);
		if (fileConfiguration == null || configFile == null) {
			return;
		} else {
			try {
				getConfig().save(configFile);
			} catch (IOException ex) {
				plugin.getLogger().log(Level.SEVERE,
						"Could not save config to " + configFile, ex);
			}
		}
	}

	public void saveDefaultConfig() {
		File dataFolder = plugin.getDataFolder();
		if (dataFolder == null)
			throw new IllegalStateException();
		configFile = new File(dataFolder, fileName);
		if (!configFile.exists()) {
			this.plugin.saveResource(fileName, false);
		}
	}

}
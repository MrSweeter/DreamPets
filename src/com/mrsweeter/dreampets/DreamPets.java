package com.mrsweeter.dreampets;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.mrsweeter.dreampets.Commands.Commands;
import com.mrsweeter.dreampets.Listeners.JoinQuit;
import com.mrsweeter.dreampets.Listeners.PetsListener;
import com.mrsweeter.dreampets.Listeners.PlayerMove;
import com.mrsweeter.dreampets.Utils.Color;
import com.mrsweeter.dreampets.Utils.Language;
import com.mrsweeter.dreampets.Utils.PluginConfiguration;

public class DreamPets extends JavaPlugin	{
	
	public static final Logger LOG = Logger.getLogger("Minecraft - DreamPets");
	public static final PluginManager PM = Bukkit.getPluginManager();
	
	Map<String, PluginConfiguration> configs = new HashMap<String, PluginConfiguration>();
	
	public static Map<String, BukkitRunnable> petsPlayer = new HashMap<>();
	public static Map<String, Dual> pets = new HashMap<>();
	
	public void onEnable()	{
		
		configs.put("pets", new PluginConfiguration(this, "pets.yml"));
		configs.put("config", new PluginConfiguration(this, "configuration.yml", "configuration.yml", null));
		
		getCommand("pets").setExecutor(new Commands(this));
		getCommand("petsbuy").setExecutor(new Commands(this));
		getCommand("petscustom").setExecutor(new Commands(this));
		getCommand("petsreload").setExecutor(new Commands(this));
		
		PM.registerEvents(new JoinQuit(this), this);
		PM.registerEvents(new PlayerMove(this), this);
		PM.registerEvents(new PetsListener(), this);
		
		LOG.info(Color.GREEN + "=============== " + Color.YELLOW + "DreamPets enable" + Color.GREEN + " ===============" + Color.RESET);
		
	}
	
	public void onDisable()	{
		
		reload(null);
		LOG.info(Color.GREEN + "=============== " + Color.YELLOW + "DreamPets disable" + Color.GREEN + " ===============" + Color.RESET);
		
	}
	
	public PluginConfiguration getAConfig(String name)	{
		PluginConfiguration pc = configs.get(name);
		if (pc == null)	{
			String file = name + ".yml";
			pc = new PluginConfiguration(this, file);
			configs.put(name, pc);
		}
		return pc;
	}
	
	public Map<String, PluginConfiguration> getAllConfig()	{
		return configs;
	}
	
	public boolean reload(CommandSender sender) {
		
		for (String uuid : pets.keySet())	{
			if (DreamPets.petsPlayer.containsKey(uuid))	{
				petsPlayer.get(uuid).cancel();
				DreamPets.petsPlayer.remove(uuid);
			}
			pets.get(uuid).getA().remove();
			if (DreamPets.pets.get(uuid).getB() != null)	{
				DreamPets.pets.get(uuid).getB().remove();
			}
		}
		if (sender != null)	{
			sender.sendMessage(Language.reload);
		}
		return true;
		
	}
}

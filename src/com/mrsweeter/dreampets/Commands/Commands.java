package com.mrsweeter.dreampets.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.mrsweeter.dreampets.DreamPets;
import com.mrsweeter.dreampets.Utils.Language;

public class Commands implements CommandExecutor, TabCompleter	{
	
	private DreamPets pl;

	public Commands(DreamPets dreamHelper) {
		pl = dreamHelper;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		
		if (sender.hasPermission(command.getPermission()))	{
			switch (commandLabel)	{
			case "pets":
				return ExecuteCommands.pets(pl, sender, args);
			case "petsbuy":
				return ExecuteCommands.buyPets(pl, sender, args);
			case "petscustom":
				return ExecuteCommands.custom(pl, sender, args);
			case "petsreload":
				for (String key : pl.getAllConfig().keySet())	{
					pl.getAConfig(key).reload();
				}
				return pl.reload(sender);
			}
		} else {
			sender.sendMessage(Language.noPerm);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String commandLabel, String[] args) {
		List<String> complete = new ArrayList<String>();
		if (sender instanceof Player)	{
			Player p = (Player) sender;
			String uuid = p.getUniqueId().toString();
			ConfigurationSection c = pl.getAConfig("pets").getConfigurationSection(uuid + ".pets");
			
			switch (commandLabel)	{
			case "pets":
				for (String str : c.getKeys(false))	{
					complete.add(str);
				}
				break;
			case "petsbuy":
				complete = pl.getAConfig("config").getStringList("allowed-pets");
				break;
			case "petscustom":
				if (args.length == 1)	{
					for (String str : c.getKeys(false))	{
						complete.add(str);
					}
				} else if (args.length == 2)	{
					complete.add(c.getString(args[0].toUpperCase()));
				} else if (args.length == 3)	{
					complete.add("true");
					complete.add("false");
				} else if (args.length == 4)	{
					complete.add("10");
					complete.add("15");
					complete.add("25");
				}
				break;
			case "petsreload":
				
			}
		}
		return complete;
	}
}

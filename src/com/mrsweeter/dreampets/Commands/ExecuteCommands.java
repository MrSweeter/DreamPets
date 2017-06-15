package com.mrsweeter.dreampets.Commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.mrsweeter.dreampets.DreamPets;
import com.mrsweeter.dreampets.Dual;
import com.mrsweeter.dreampets.Listeners.PlayerMove;
import com.mrsweeter.dreampets.Tasks.Pets;
import com.mrsweeter.dreampets.Utils.Language;
import com.mrsweeter.dreampets.Utils.PluginConfiguration;

public class ExecuteCommands {
	
	public static boolean pets(DreamPets pl, CommandSender sender, String[] args)	{
		
		if (sender instanceof Player)	{
			
			Player p = (Player) sender;
			String uuid = p.getUniqueId().toString();
			
			if (args.length == 1)	{
				
				if (DreamPets.pets.containsKey(uuid))	{
					String str = DreamPets.pets.get(uuid).getA().getType().toString();
					p.sendMessage(Language.petsDisable.replace("{ENTITY}", str));
					if (DreamPets.petsPlayer.containsKey(uuid))	{
						DreamPets.petsPlayer.get(uuid).cancel();
						DreamPets.petsPlayer.remove(uuid);
					}
					DreamPets.pets.get(uuid).getA().remove();
					if (DreamPets.pets.get(uuid).getB() != null)	{
						DreamPets.pets.get(uuid).getB().remove();
					}
				}
				
				PluginConfiguration pets = pl.getAConfig("pets");
				
				if (pets.isSet(uuid + ".pets"))	{
					
					ConfigurationSection sectionPlayer = pets.getConfigurationSection(uuid);
					ConfigurationSection sectionPets = sectionPlayer.getConfigurationSection("pets");
					
					if (!sectionPlayer.isSet("enable-AI"))	{
						sectionPlayer.set("enable-AI", false);
						pets.save();
					}
					if (!sectionPlayer.isSet("distance-AI"))	{
						sectionPlayer.set("distance-AI", 10);
						pets.save();
					}
					
					if (sectionPets.contains(args[0].toUpperCase()))	{
						
						String name = sectionPets.getString(args[0].toUpperCase());
						
						EntityType ent = EntityType.valueOf(args[0].toUpperCase());
						
						if (!sectionPlayer.getBoolean("enable-AI"))	{
							new BukkitRunnable() {
						        
					            @Override
					            public void run() {
					            	BukkitRunnable task = new Pets((Player) sender, ent, name);
					        		task.runTaskTimer(pl, 0, 0);
					        		p.sendMessage(Language.petsEnable.replace("{ENTITY}", ent.toString()));
					        		DreamPets.petsPlayer.put(uuid, task);
					            }
					        }.runTaskLater(pl, 20);
					        
						} else {
							Entity pet = p.getWorld().spawnEntity(PlayerMove.teleportToRight(p, p), ent);
							if (!name.equals(""))	{
								pet.setCustomName(name.replace("&", "§"));
								pet.setCustomNameVisible(true);
							}
							
							Wolf wolf = (Wolf) p.getWorld().spawnEntity(PlayerMove.teleportToRight(p, p), EntityType.WOLF);
			            	wolf.setOwner(p);
			            	wolf.setSilent(true);
			            	wolf.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 9, false, false), false);
			            	wolf.setCollidable(false);
			            	((LivingEntity) pet).setCollidable(false);
			            	((LivingEntity) pet).setAI(false);
							new BukkitRunnable() {
						        
					            @Override
					            public void run() {
					            	wolf.setTarget(null);
					        		pet.teleport(wolf);
					            }
					        }.runTaskTimer(pl, 0, 1);
							
							p.sendMessage(Language.petsEnable.replace("{ENTITY}", ent.toString()));
							DreamPets.pets.put(uuid, new Dual(pet, wolf));
						}
					} else {
						p.sendMessage(Language.noPet.replace("{ENTITY}", args[0].toUpperCase()));
					}
				} else {
					p.sendMessage(Language.noPetBuy);
				}
			} else if (args.length == 0)	{
				if (DreamPets.pets.containsKey(uuid))	{
					String str = DreamPets.pets.get(uuid).getA().getType().toString();
					p.sendMessage(Language.petsDisable.replace("{ENTITY}", str));
					if (DreamPets.petsPlayer.containsKey(uuid))	{
						DreamPets.petsPlayer.get(uuid).cancel();
						DreamPets.petsPlayer.remove(uuid);
					}
					DreamPets.pets.get(uuid).getA().remove();
					if (DreamPets.pets.get(uuid).getB() != null)	{
						DreamPets.pets.get(uuid).getB().remove();
					}
				}
			}
		} else {
			sender.sendMessage(Language.onlyPlayer);
		}
		return true;
	}
	
	public static boolean buyPets(DreamPets pl, CommandSender sender, String[] args)	{
		
		if (sender instanceof Player)	{
			
			Player p = (Player) sender;
			String uuid = p.getUniqueId().toString();
			
			if (args.length == 1)	{
				
				PluginConfiguration config = pl.getAConfig("config");
				List<String> l = config.getStringList("allowed-pets");
				
				if (l.contains(args[0].toUpperCase()))	{
					
					String ent = args[0].toUpperCase();
					ConfigurationSection section;
					
					PluginConfiguration pets = pl.getAConfig("pets");
					if (pets.contains(uuid))	{
						section = pets.getConfigurationSection(uuid + ".pets");
					} else {
						section = pets.createSection(uuid + ".pets");
						pets.save();
					}
					
					if (!section.getKeys(false).contains(ent))	{
						
						section.set(ent, "");
						pets.save();
						sender.sendMessage(Language.petBuy.replace("{ENTITY}", args[0].toUpperCase()));
					} else {
						sender.sendMessage(Language.alreadyBuy.replace("{ENTITY}", args[0].toUpperCase()));
					}
				} else {
					sender.sendMessage(Language.petForbidden.replace("{ENTITY}", args[0].toUpperCase()));
				}
			} else {
				return false;
			}
		} else {
			sender.sendMessage(Language.onlyPlayer);
		}
		return true;
	}
	
	public static boolean custom(DreamPets pl, CommandSender sender, String[] args) {
		
		if (sender instanceof Player)	{
			if (args.length > 1)	{
				Player p = (Player) sender;
				String uuid = p.getUniqueId().toString();
				
				PluginConfiguration pets = pl.getAConfig("pets");
				if (pets.isSet(uuid + ".pets." + args[0].toUpperCase()))	{
					
					String msg = Language.modification;
					
					ConfigurationSection section = pets.getConfigurationSection(uuid + ".pets");
					
					section.set(args[0].toUpperCase(), args[1]);
					msg += "§7Name, ";
					
					if (args.length > 2 && (args[2].equalsIgnoreCase("false") || args[2].equalsIgnoreCase("true")))	{
						
						if (args[2].equalsIgnoreCase("false"))	{
							pets.set(uuid + ".enable-AI", Boolean.FALSE);
						} else if (args[2].equalsIgnoreCase("true"))	{
							pets.set(uuid + ".enable-AI", Boolean.TRUE);
						}
						p.sendMessage(Language.actuPet);
						msg += "§7AI, ";
					}
					if (args.length > 3 && isInteger(args[3]) && Integer.parseInt(args[3]) > 14)	{
						pets.set(uuid + ".distance-AI", Integer.parseInt(args[3]));
						msg += "§7Distance";
					}
					pets.save();				
					p.sendMessage(msg);
					
				} else {
					p.sendMessage(Language.noPet.replace("{ENTITY}", args[0].toUpperCase()));
				}
			} else {
				return false;
			}
		} else {
			sender.sendMessage(Language.onlyPlayer);
		}
		return true;
	}
	
	private static boolean isInteger(String str)	{
		try {
			Integer.parseInt(str);
		} catch (Exception e)	{
			return false;
		}
		return true;
	}
}

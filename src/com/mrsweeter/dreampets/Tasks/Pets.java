package com.mrsweeter.dreampets.Tasks;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.mrsweeter.dreampets.DreamPets;
import com.mrsweeter.dreampets.Dual;

public class Pets extends BukkitRunnable	{
	
	Player p;
	Entity pet;
	boolean AI;
	
	public Pets(Player pP, EntityType ent, String name)	{
		p = pP;
		pet = pP.getWorld().spawnEntity(teleportToRight(), ent);
		if (!name.equals(""))	{
			pet.setCustomName(name.replace("&", "§"));
			pet.setCustomNameVisible(true);
		}
		teleportToRight();
		DreamPets.pets.put(p.getUniqueId().toString(), new Dual(pet, null));
	}

	@Override
	public void run() {
		
		Location loc = teleportToRight();
		((LivingEntity) pet).setAI(true);
		pet.teleport(loc);
		((LivingEntity) pet).setAI(false);
		
	}
	
	private Location teleportToRight()	{
		
		int yaw = (int) p.getLocation().getYaw();
		while (yaw < 0)	{yaw += 360;}
		Location petloc;
		if (pet == null)	{
			petloc = p.getLocation();
		} else {
			petloc = pet.getLocation();
		}
		
		if (yaw >= 338 || yaw < 23) {
			
			petloc.setX(p.getLocation().getX()-2);
			petloc.setZ(p.getLocation().getZ());
			
		} else if (yaw >= 23 && yaw < 68) {
			
			petloc.setX(p.getLocation().getX()-1.5);
			petloc.setZ(p.getLocation().getZ()-1.5);
			
		} else if (yaw >= 68 && yaw < 113) {
			
			petloc.setZ(p.getLocation().getZ()-2);
			petloc.setX(p.getLocation().getX());
			
		} else if (yaw >= 113 && yaw < 158) {
			
			petloc.setX(p.getLocation().getX()+1.5);
			petloc.setZ(p.getLocation().getZ()-1.5);
			
		} else if (yaw >= 158 && yaw < 203) {
			
			petloc.setX(p.getLocation().getX()+2);
			petloc.setZ(p.getLocation().getZ());
			
		} else if (yaw >= 203 && yaw < 248) {
			
			petloc.setX(p.getLocation().getX()+1.5);
			petloc.setZ(p.getLocation().getZ()+1.5);
			
		} else if (yaw >= 248 && yaw < 293) {
			
			petloc.setZ(p.getLocation().getZ()+2);
			petloc.setX(p.getLocation().getX());
			
		} else if (yaw >= 293 && yaw < 338) {
			
			petloc.setX(p.getLocation().getX()-1.5);
			petloc.setZ(p.getLocation().getZ()+1.5);
		}
		
		petloc.setYaw(p.getLocation().getYaw());
		petloc.setPitch(p.getLocation().getPitch());
		petloc.setY(p.getLocation().getY());
		
		return petloc;
	}
}

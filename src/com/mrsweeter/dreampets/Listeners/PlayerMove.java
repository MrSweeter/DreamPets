package com.mrsweeter.dreampets.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.mrsweeter.dreampets.DreamPets;

public class PlayerMove implements Listener	{
	
	DreamPets pl;
	
	public PlayerMove(DreamPets main)	{
		pl = main;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerMoveCheckPets(PlayerMoveEvent event)	{
		
		String uuid = event.getPlayer().getUniqueId().toString();
		
		if (!DreamPets.petsPlayer.containsKey(uuid) && DreamPets.pets.containsKey(uuid))	{
			
			Entity ent = DreamPets.pets.get(uuid).getA();
			Entity w = DreamPets.pets.get(uuid).getB();
			Location pLoc = event.getPlayer().getLocation();
			Location petLoc = ent.getLocation();
			
			int dist = pl.getAConfig("pets").getInt(uuid + ".distance-AI");
			if (pLoc.distance(petLoc) > dist)	{
				w.teleport(teleportToRight(event.getPlayer(), w));
			}			
		}
	}
	
	public static Location teleportToRight(Player p, Entity pet)	{
		
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

package com.mrsweeter.dreampets.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.mrsweeter.dreampets.DreamPets;

public class JoinQuit implements Listener {
	
	public JoinQuit(DreamPets main) {
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event)	{
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event)	{
		
		String uuid = event.getPlayer().getUniqueId().toString();
		
		if (DreamPets.pets.containsKey(uuid))	{
			
			if (DreamPets.petsPlayer.containsKey(uuid))	{
				BukkitRunnable task = DreamPets.petsPlayer.get(uuid);
				task.cancel();
				DreamPets.petsPlayer.remove(uuid);
			}
			DreamPets.pets.get(uuid).getA().remove();
			if (DreamPets.pets.get(uuid).getB() != null)	{
				DreamPets.pets.get(uuid).getB().remove();
			}
			DreamPets.pets.remove(uuid);
			
		}
		
	}
}

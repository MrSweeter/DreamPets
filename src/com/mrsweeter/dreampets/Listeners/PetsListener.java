package com.mrsweeter.dreampets.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.mrsweeter.dreampets.DreamPets;
import com.mrsweeter.dreampets.Dual;

public class PetsListener implements Listener {
	
	@EventHandler
	public void onPetsDamage(EntityDamageEvent event)	{
		Entity ent = event.getEntity();
		
		for (Dual d : DreamPets.pets.values())	{
			if (d.isValid(ent))	{
				event.setCancelled(true);
			}
		}
	}
}

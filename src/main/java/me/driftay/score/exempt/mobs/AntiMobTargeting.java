package me.driftay.score.exempt.mobs;

import me.driftay.score.commands.handlers.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class AntiMobTargeting implements Listener {
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityTarget(EntityTargetEvent event){
		event.setCancelled(true);
	}
}

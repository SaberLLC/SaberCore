package me.driftay.score.exempt.mobs;

import me.driftay.score.utils.Util;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class AntiMobMoving implements Listener {

	/*
	 * Stops entites from moving such as pigs, cows, blazes etc..
	 * but the it dosen't work on mobs that can bounce or fly(ghast, guardians & slime/magma cubes(dosen't when broken down)).
	 * */

	public List<String> entList = Util.config.getStringList("Anti-Mob-Movement");

	@EventHandler(priority = EventPriority.LOW)
	public void entitySpawnEvent(EntitySpawnEvent event) {
		if(entList.isEmpty()) return;
		if (event.getEntity().getType() == EntityType.DROPPED_ITEM || event.getEntity().getType() == EntityType.PRIMED_TNT) return;
		if (event.getEntity() instanceof Player) return;

		if(!entList.contains(event.getEntity().getType().toString())) return;
		final LivingEntity entity = (LivingEntity) event.getEntity();
		entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 25));
	}
}

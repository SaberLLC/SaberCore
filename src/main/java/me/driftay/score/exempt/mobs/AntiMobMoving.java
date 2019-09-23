package me.driftay.score.exempt.mobs;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AntiMobMoving implements Listener {

	/*
	 * Stops entites from moving such as pigs, cows, blazes etc..
	 * but the it dosen't work on mobs that can bounce or fly(ghast, guardians & slime/magma cubes(dosen't when broken down)).
	 * */
	@EventHandler(priority = EventPriority.LOW)
	public final void entitySpawnEvent(EntitySpawnEvent event) {

		if (event.getEntity().getType() == EntityType.DROPPED_ITEM || event.getEntity().getType() == EntityType.PRIMED_TNT)
			return;

		if (event.getEntity() instanceof Player)
			return;

		final LivingEntity entity = (LivingEntity) event.getEntity();
		entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 25));
	}
}

package me.driftay.score.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class RoofCannonTask extends BukkitRunnable implements Listener {
	/*
	 * Gets all of the loaded chunks in each world then checks to see if the entity is a FallingBlock (Sand, Tnt and Gravel).
	 * If the entity is and the location is above or at 255.125 in the y direction the entity gets removed.
	 */
	@Override
	public void run() {
		for (World world : Bukkit.getWorlds()) {
			for (Entity entity : world.getEntities()) {
				// Handles the rest of the Falling blocks.
				if (entity instanceof FallingBlock && entity.getLocation().getY() >= 255)
					entity.remove();
				// Handles the TNT primed
				if (entity instanceof TNTPrimed && entity.getLocation().getY() >= 255)
					entity.remove();
			}
		}
	}

	/*
	 * Handles when a FallingBlock (Sand and Gravel) turns into a solid block, and if changes at 255 it cancels the change from the entity
	 * to a solid block then removes the block to prevent players from using the sand or gravel as a back board.
	 */
	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof FallingBlock && entity.getLocation().getY() == 255) {
			event.setCancelled(true);
			// Might have to be set to water in the future if users report the initial block is set to air and not water.
			event.getBlock().setType(Material.AIR);
		}
	}
}

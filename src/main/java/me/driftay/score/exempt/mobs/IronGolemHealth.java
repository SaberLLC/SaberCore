package me.driftay.score.exempt.mobs;

import me.driftay.score.utils.Util;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class IronGolemHealth implements Listener {

	//Changes the health of the iron golem so it dies faster. I can't think of a class name...

	@EventHandler
	public void onIronGolemSpawn(CreatureSpawnEvent event) {
		if (event.getEntity().getType() != EntityType.IRON_GOLEM)
			return;

		IronGolem ironGolem = (IronGolem) event.getEntity();
		ironGolem.setHealth(Util.config.getDouble("ironGolemHealth"));
	}
}

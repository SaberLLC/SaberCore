package me.driftay.score.exempt;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class NaturalMobSpawning implements Listener {

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            e.setCancelled(true);
        }
    }
}

package me.driftay.score.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class AntiCreeperGlitch implements Listener {

    @EventHandler
    public void onCreeperGlitch(EntityDamageEvent e) {
        if (!e.getEntity().getType().equals(EntityType.CREEPER)) {
            return;
        }
        if (e.getCause().equals(EntityDamageEvent.DamageCause.DROWNING) || e.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION)) {
            e.getEntity().remove();
        }
    }
}

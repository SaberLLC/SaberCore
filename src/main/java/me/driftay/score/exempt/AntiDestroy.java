package me.driftay.score.exempt;

import me.driftay.score.config.Config;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class AntiDestroy implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getCause() != EntityDamageEvent.DamageCause.LIGHTNING) {
            return;
        }
        if (!(e.getEntity() instanceof Item)) {
            return;
        }
        Material type = ((Item) e.getEntity()).getItemStack().getType();
        if (!Config.lightningDeny.contains(type.name())) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onEventLava(EntityDamageEvent e) {
        if (e.getEntity().getType() != EntityType.DROPPED_ITEM) {
            return;
        }
        if (!(e.getEntity() instanceof Item)) {
            return;
        }
        if (e.getCause() != EntityDamageEvent.DamageCause.LAVA && e.getCause() != EntityDamageEvent.DamageCause.FIRE && e.getCause() != EntityDamageEvent.DamageCause.FIRE_TICK) {
            return;
        }
        Material type = ((Item) e.getEntity()).getItemStack().getType();
        if (!Config.lavaBurnDeny.contains(type.name())) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onEventExplosion(EntityDamageEvent e) {
        if (e.getEntity().getType() != EntityType.DROPPED_ITEM) {
            return;
        }
        if (!(e.getEntity() instanceof Item)) {
            return;
        }
        if (e.getCause() != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && e.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            return;
        }
        Material type = ((Item) e.getEntity()).getItemStack().getType();
        if (!Config.explosionDeny.contains(type.name())) {
            return;
        }
        e.setCancelled(true);
    }
}

package me.driftay.score.exempt;

import me.driftay.score.utils.Util;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class AntiDestroy implements Listener {

    private List<String> lightningDeny = Util.config.getStringList("Lightning-Deny");
    private List<String> explosionDeny = Util.config.getStringList("Explosion-Deny");
    private List<String> lavaBurnDeny = Util.config.getStringList("Burn-Deny");

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getCause() != EntityDamageEvent.DamageCause.LIGHTNING) {
            return;
        }
        if (!(e.getEntity() instanceof Item)) {
            return;
        }
        Material type = ((Item) e.getEntity()).getItemStack().getType();
        if (!lightningDeny.contains(type.name())) {
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
        if (!lavaBurnDeny.contains(type.name())) {
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
        if (!explosionDeny.contains(type.name())) {
            return;
        }
        e.setCancelled(true);
    }
}

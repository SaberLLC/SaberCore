package me.driftay.score.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;

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

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack i = p.getItemInHand();
        Block b = event.getClickedBlock();
        if (i.getTypeId() != 383) {
            return;
        }
        if (b == null) {
            return;
        }
        if (b.getType() != Material.MOB_SPAWNER) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        SpawnEgg egg = (SpawnEgg) i.getData();
        p.getWorld().spawnEntity(b.getLocation(), egg.getSpawnedType());
        if (i.getAmount() >= 2) {
            i.setAmount(i.getAmount() - 1);
            p.setItemInHand(i);
        } else {
            p.setItemInHand(new ItemStack(Material.AIR));
        }
        event.setCancelled(true);
    }
}

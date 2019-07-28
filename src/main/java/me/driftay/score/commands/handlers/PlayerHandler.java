package me.driftay.score.commands.handlers;

import me.driftay.score.SaberCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class PlayerHandler implements Listener {

    public static Set<String> jellyLegs = new HashSet<>();
    public static Set<String> nightvision = new HashSet<>();
    public static Set<String> bottleRecycle = new HashSet<>();

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL || event.getEntity().getType() != EntityType.PLAYER)
            return;

        if (jellyLegs.contains(event.getEntity().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void OnDrinkPotion(PlayerItemConsumeEvent event) {
        if (event.getItem() == null
                || event.getItem().getType() != Material.POTION) return;

        if (bottleRecycle.contains(event.getPlayer().getName())) {
            Bukkit.getScheduler().runTaskLater(SaberCore.instance, () -> {
                ItemStack itemInHand = event.getPlayer().getItemInHand();
                if (itemInHand.getType() == Material.GLASS_BOTTLE) {
                    event.getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
                } else if (itemInHand.getType() == Material.POTION) {
                    event.getPlayer().getInventory().removeItem(new ItemStack(Material.GLASS_BOTTLE, 1));
                }

            }, 1);
        }
    }
}

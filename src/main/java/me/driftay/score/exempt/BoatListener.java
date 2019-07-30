package me.driftay.score.exempt;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BoatListener implements Listener {

    @EventHandler
    public void onBoatPlace(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getItemInHand().getType().name().contains("BOAT")) {
            event.setCancelled(true);
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getItemInHand().getType().name().contains("MINECART")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDispenseItem(BlockDispenseEvent event) {
        if (event.getItem().getType().name().contains("BOAT")) {
            event.setCancelled(true);
        } else if (event.getItem().getType().name().contains("MINECART")) {
            event.setCancelled(true);
        }
    }
}
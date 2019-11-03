package me.driftay.score.listeners;

import me.driftay.score.SaberCore;
import me.driftay.score.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DenySpawnerStorage implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.isCancelled()) return;

        Inventory clicked = e.getClickedInventory();

        if (e.getClick().isShiftClick()) {
            if (clicked == e.getWhoClicked().getInventory()) {
                ItemStack clickedOn = e.getCurrentItem();
                if (clickedOn != null && SaberCore.instance.itemList.contains(clickedOn.getType().toString())) {
                    player.sendMessage(Message.CANNOT_STORE_ITEM.getMessage().replace("{item}", clickedOn.getType().toString()));
                    e.setCancelled(true);
                }
            }
        }

        if (clicked != e.getWhoClicked().getInventory()) {
            ItemStack onCursor = e.getCursor();
            if (onCursor != null && SaberCore.instance.itemList.contains(onCursor.getType().toString())) {
                player.sendMessage(Message.CANNOT_STORE_ITEM.getMessage().replace("{item}", onCursor.getType().toString()));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.isCancelled()) return;

        ItemStack dragged = e.getOldCursor();
        if (SaberCore.instance.itemList.contains(dragged.getType().toString())) {
            int inventorySize = e.getInventory().getSize();
            for (int i : e.getRawSlots()) {
                if (i < inventorySize) {
                    p.sendMessage(Message.CANNOT_STORE_ITEM.getMessage().replace("{item}", dragged.getType().toString()));
                    e.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onHopperMoveEvent(InventoryMoveItemEvent e) {
        if (e.isCancelled()) return;

        if (SaberCore.instance.itemList.contains(e.getItem().getType().toString())) {
            e.setCancelled(true);
        }
    }
}

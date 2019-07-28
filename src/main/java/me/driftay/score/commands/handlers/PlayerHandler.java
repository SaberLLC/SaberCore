package me.driftay.score.commands.handlers;

import me.driftay.score.SaberCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

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

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getView().getTopInventory();

        if(inventory != null && inventory.getType() == InventoryType.BREWING) {
            if(event.getClick() == ClickType.NUMBER_KEY && (event.getRawSlot() == 0 || event.getRawSlot() == 1 || event.getRawSlot() == 2)) {
                event.setCancelled(true);
                return;
            }

            if(event.getClick().name().contains("SHIFT") && event.getCurrentItem().getAmount() > 1) {
                Player player = (Player) event.getWhoClicked();
                ItemStack stack = event.getCurrentItem();
                ItemStack newStack = new ItemStack(stack);

                newStack.setAmount(stack.getAmount() - 1);
                stack.setAmount(1);

                Bukkit.getScheduler().runTask(SaberCore.getInstance(), () -> {
                    if(player.getInventory().getItem(event.getSlot()) == null) {
                        player.getInventory().setItem(event.getSlot(), newStack);
                    } else {
                        stack.setAmount(newStack.getAmount() + 1);
                    }

                    player.updateInventory();
                });
            }
        }
    }
}

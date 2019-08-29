package me.driftay.score.commands.handlers;

import me.driftay.score.config.Conf;
import me.driftay.score.utils.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class HarvesterHoeListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        if (event.isCancelled())
            return;

        if (event.getPlayer().getItemInHand().getType() != Material.DIAMOND_HOE
                || event.getPlayer().getItemInHand() == null
                || !event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(Util.color(Conf.harvesterHoeDisplayName)))
            return;


            if (Util.isHooked() && Conf.harvesterHoeAutoSell) {
                if (event.getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
                    event.setCancelled(true);
                    Location currLoc = event.getBlock().getLocation();
                    while (currLoc.getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
                        currLoc = new Location(currLoc.getWorld(), currLoc.getBlockX(), currLoc.getBlockY() + 1, currLoc.getBlockZ());
                    }
                    currLoc = new Location(currLoc.getWorld(), currLoc.getBlockX(), currLoc.getBlockY() - 1, currLoc.getBlockZ());
                    while (currLoc.getBlockY() >= event.getBlock().getY()) {
                        currLoc.getBlock().setType(Material.AIR);
                        Util.sell(event.getPlayer(), Conf.perCanePrice);
                        currLoc = new Location(currLoc.getWorld(), currLoc.getBlockX(), currLoc.getBlockY() - 1, currLoc.getBlockZ());
                    }
                }
            } else {
                if (event.getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
                    event.setCancelled(true);
                    Location currLoc = event.getBlock().getLocation();
                    while (currLoc.getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
                        currLoc = new Location(currLoc.getWorld(), currLoc.getBlockX(), currLoc.getBlockY() + 1, currLoc.getBlockZ());
                    }
                    currLoc = new Location(currLoc.getWorld(), currLoc.getBlockX(), currLoc.getBlockY() - 1, currLoc.getBlockZ());
                    while (currLoc.getBlockY() >= event.getBlock().getY()) {
                        currLoc.getBlock().setType(Material.AIR);
                        givePlayerItem(event.getPlayer(), Material.SUGAR_CANE);
                        currLoc = new Location(currLoc.getWorld(), currLoc.getBlockX(), currLoc.getBlockY() - 1, currLoc.getBlockZ());
                    }
                }
            }
    }


    private void givePlayerItem(Player player, Material m) {
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(new ItemStack(m));
        } else if (getSlot(player, m) != -1) {
            player.getInventory().addItem(new ItemStack(m));
        } else {
            player.getWorld().dropItem(player.getLocation(), new ItemStack(m));
        }
    }

    private int getSlot(Player p, Material m) {
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            if ((p.getInventory().getItem(i).getType() == m) && (p.getInventory().getItem(i).getAmount() < p.getInventory().getItem(i).getMaxStackSize())) {
                return i;
            }
        }
        return -1;
    }
}

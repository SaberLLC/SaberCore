package me.driftay.score.commands.handlers;

import com.massivecraft.factions.*;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import itemnbtapi.NBTItem;
import me.driftay.score.utils.Util;
import me.driftay.score.utils.XMaterial;
import net.coreprotect.CoreProtect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class ShockwaveListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onShockwave(BlockBreakEvent e) {
        if (e.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) ||
                e.getPlayer().getItemInHand().getType().equals(XMaterial.DIAMOND_SHOVEL.parseMaterial()) ||
                e.getPlayer().getItemInHand().getType() == Material.DIAMOND_HOE) {
            NBTItem nbti = new NBTItem(e.getPlayer().getItemInHand());
            if (nbti.hasKey("Shockwave")) {
                if (nbti.getBoolean("Shockwave")) {
                    int radius = nbti.getInteger("Radius");
                    shockwaveBreak(e, radius);
                }
            }
        }
    }

    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }

        return (WorldGuardPlugin) plugin;
    }

    @SuppressWarnings("deprecation")
    public void shockwaveBreak(BlockBreakEvent e, int radius) {
        Player p = e.getPlayer();
        Faction UUIDfac;
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(e.getPlayer());
        UUIDfac = fplayer.getFaction();

        boolean spade = false;
        if (e.getPlayer().getItemInHand().getType().equals(XMaterial.DIAMOND_SHOVEL.parseMaterial())) {
            spade = true;
        }
        int goesInto = radius / 2;
        radius = radius - (goesInto + 1);
        for (double x = e.getBlock().getLocation().getX() - radius; x <= e.getBlock().getLocation().getX() + radius; x++) {
            for (double y = e.getBlock().getLocation().getY() - radius; y <= e.getBlock().getLocation().getY() + radius; y++) {
                for (double z = e.getBlock().getLocation().getZ() - radius; z <= e.getBlock().getLocation().getZ() + radius; z++) {
                    Location loc = new Location(e.getBlock().getWorld(), x, y, z);
                    if (loc.getBlock().getType() == Material.BEDROCK || loc.getBlock().getType() == Material.AIR || loc.getBlock().getType() == XMaterial.SPAWNER.parseMaterial()) {
                        continue;
                    }
                    if (!Objects.requireNonNull(getWorldGuard()).canBuild(e.getPlayer(), loc)) {
                        continue;
                    }
                    if (spade) {
                        if (loc.getBlock().getType() != XMaterial.DIRT.parseMaterial()
                                && loc.getBlock().getType() != XMaterial.GRAVEL.parseMaterial()
                                && loc.getBlock().getType() != XMaterial.SAND.parseMaterial()
                                && loc.getBlock().getType() != XMaterial.GRASS.parseMaterial()) {
                            continue;
                        }
                    }

                    FLocation floc = new FLocation(loc);
                    Faction fac = Board.getInstance().getFactionAt(floc);
                    if (fac.isWilderness() || fac.getId().equals(UUIDfac.getId())) {
                        p.getInventory().addItem(new ItemStack(loc.getBlock().getType()));
                        loc.getBlock().setType(Material.AIR);
                        if(Bukkit.getPluginManager().getPlugin("CoreProtect") != null) {
                            if (Util.config.getBoolean("Shockwave.Log-Core-Protect")) {
                                CoreProtect.getInstance().getAPI().logRemoval(p.getName(), loc, loc.getBlock().getType(), loc.getBlock().getData());
                            }
                        }
                    }
                }
            }
        }
    }
}


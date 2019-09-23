package me.driftay.score.commands.handlers;

import com.massivecraft.factions.*;
import com.massivecraft.factions.zcore.nbtapi.NBTItem;
import me.driftay.score.utils.XMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import static me.driftay.score.utils.Util.getWorldGuard;

public class SaberPicksListener implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent e) {
        if (e.getPlayer().getItemInHand().getType().equals(XMaterial.DIAMOND_PICKAXE.parseMaterial()) ||
                e.getPlayer().getItemInHand().getType().equals(XMaterial.DIAMOND_SHOVEL.parseMaterial()) ||
                e.getPlayer().getItemInHand().getType() == XMaterial.DIAMOND_AXE.parseMaterial()) {
            NBTItem nbti = new NBTItem(e.getPlayer().getItemInHand());
            if (nbti.hasKey("SaberTool")) {
                if (nbti.getBoolean("SaberTool")) {
                    int radius = nbti.getInteger("Radius");
                    saberPickBreak(e, radius);
                }
            }
        }
    }


    @SuppressWarnings("deprecation")
    public void saberPickBreak(BlockBreakEvent e, int radius) {
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
                    if(getWorldGuard() != null) {
                        if (!getWorldGuard().canBuild(e.getPlayer(), loc)) {
                            continue;
                        }
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
                    }
                }
            }
        }
    }
}

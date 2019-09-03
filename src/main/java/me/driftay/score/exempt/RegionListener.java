package me.driftay.score.exempt;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.driftay.score.config.Conf;
import me.driftay.score.utils.Util;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class RegionListener implements Listener {

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent e) {
        Block block = e.getBlock();
        int id = block.getTypeId();
        if (id >= 8 && id <= 11) {
            ProtectedRegion region = null;
            for (String s : Conf.blockedRegions) {
                if(Util.getWorldGuard() != null) {
                    ProtectedRegion temp = Util.getWorldGuard().getRegionManager(block.getWorld()).getRegion(s);
                    if (temp != null) {
                        region = temp;
                        break;
                    }
                }
            }
            if (region != null) {
                Location location = e.getToBlock().getLocation();
                Vector v = new Vector(location.getX(), location.getY(), location.getZ());
                if (region.contains(v)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}

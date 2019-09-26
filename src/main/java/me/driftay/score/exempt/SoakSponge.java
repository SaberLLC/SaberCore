package me.driftay.score.exempt;

import me.driftay.score.config.Conf;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SoakSponge implements Listener {
    @EventHandler
    public void on(BlockFromToEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Block block = e.getBlock();
        World world = block.getWorld();
        int radius = Conf.spawnerSpongeRadius;
        int blockX = block.getX();
        int blockY = block.getY();
        int blockZ = block.getZ();
        for (int fromX = -(radius + 1); fromX <= radius + 1; ++fromX) {
            for (int fromY = -(radius + 1); fromY <= radius + 1; ++fromY) {
                for (int fromZ = -(radius + 1); fromZ <= radius + 1; ++fromZ) {
                    Block b = world.getBlockAt(blockX + fromX, blockY + fromY, blockZ + fromZ);
                    if (b.getType().equals(Material.SPONGE)) {
                        if (e.isCancelled()) {
                            return;
                        }
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player player = e.getPlayer();
        if (e.getBlock().getType() == Material.SPONGE) {
            for (int radius = Conf.spawnerSpongeRadius, x = -radius; x <= radius; ++x) {
                for (int y = -radius; y <= radius; ++y) {
                    for (int z = -radius; z <= radius; ++z) {
                        Location locIntorno = new Location(player.getWorld(), (double) (e.getBlock().getX() + x), (double) (e.getBlock().getY() + y), (double) (e.getBlock().getZ() + z));
                        if (locIntorno.getBlock().getType() == Material.STATIONARY_LAVA || locIntorno.getBlock().getType() == Material.LAVA || locIntorno.getBlock().getType() == Material.WATER || locIntorno.getBlock().getType() == Material.STATIONARY_WATER) {
                            locIntorno.getBlock().setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onWaterBucket(PlayerInteractEvent playerInteractEvent) {
        if (!playerInteractEvent.isCancelled() && playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            int i = Conf.spawnerSpongeRadius;
            Block clickedBlock = playerInteractEvent.getClickedBlock();
            World world = clickedBlock.getWorld();
            if (playerInteractEvent.getPlayer().getItemInHand().getType().equals(Material.WATER_BUCKET)) {
                int x = clickedBlock.getX();
                int y = clickedBlock.getY();
                int z = clickedBlock.getZ();
                for (int i2 = -i; i2 <= i; i2++) {
                    for (int i3 = -i; i3 <= i; i3++) {
                        for (int i4 = -i; i4 <= i; i4++) {
                            if (isSpawner(world.getBlockAt(x + i2, y + i3, z + i4).getType())) {
                                playerInteractEvent.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isSpawner(Material material) {
        return material.equals(Material.SPONGE);
    }
}

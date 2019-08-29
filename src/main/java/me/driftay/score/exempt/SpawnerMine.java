package me.driftay.score.exempt;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import me.driftay.score.config.Conf;
import me.driftay.score.utils.Message;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class SpawnerMine implements Listener {

    @EventHandler
    public void onSpawnerMine(BlockBreakEvent e) {
        if(!e.getBlock().getType().equals(Material.MOB_SPAWNER)) return;

        if (nearbyEnemies(e.getPlayer(), Conf.spawnerMineRadius, null) && !e.getPlayer().hasPermission("sabercore.spawnermine,bypass")) {
            e.setCancelled(true);
        }
        if(e.isCancelled()){
            e.getPlayer().sendMessage(Message.ANTI_SPAWNER_MINE_PLAYERS_NEAR.getMessage());
        }
    }

    public boolean nearbyEnemies(Player player, double d, String str) {
        List<Entity> nearbyEntities = player.getNearbyEntities(d, d, d);
        FPlayer byPlayer = FPlayers.getInstance().getByPlayer(player);
        for (Entity entity : nearbyEntities) {
            if ((entity instanceof Player) && (str == null || entity.hasPermission(str))) {
                FPlayer byPlayer2 = FPlayers.getInstance().getByPlayer((Player) entity);
                if (!(byPlayer2.getFactionId().equals(byPlayer.getFactionId()) || byPlayer2.getFaction().getRelationWish(byPlayer.getFaction()).isAlly())) {
                    return true;
                }
            }
        }
        return false;
    }
}

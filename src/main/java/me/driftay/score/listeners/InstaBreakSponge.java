package me.driftay.score.listeners;

import com.massivecraft.factions.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InstaBreakSponge implements Listener {


    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block.getType() == Material.SPONGE) {
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
            Faction faction = fPlayer.getFaction();
            Faction location = Board.getInstance().getFactionAt(new FLocation(block.getLocation()));
            if (faction.getId().equals(location.getId()) || (location.isNone())) {
                block.breakNaturally();
                event.setCancelled(true);
            }
        }
    }
}

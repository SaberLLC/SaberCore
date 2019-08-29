package me.driftay.score.exempt;

import me.driftay.score.utils.XMaterial;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class DragonEggAntiTP implements Listener {

    @EventHandler
    public void onDeggClick(PlayerInteractEvent e){
        if(!e.getItem().equals(XMaterial.DRAGON_EGG.parseItem()) || e.getAction() == null)
            return;

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            e.setCancelled(true);
        }
    }
}

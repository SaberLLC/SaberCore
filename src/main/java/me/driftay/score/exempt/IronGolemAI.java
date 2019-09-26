package me.driftay.score.exempt;

import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class IronGolemAI implements Listener {

    @EventHandler
    public void onIgTarget(EntityTargetLivingEntityEvent e){
        if(!(e.getEntity() instanceof IronGolem)) return;

        if(!(e.getTarget() instanceof Zombie)) return;

        e.setCancelled(true);
    }
}

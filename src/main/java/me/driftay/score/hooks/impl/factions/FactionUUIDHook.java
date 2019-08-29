package me.driftay.score.hooks.impl.factions;

import com.massivecraft.factions.listeners.FactionsBlockListener;
import me.driftay.score.hooks.impl.FactionHook;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class FactionUUIDHook extends FactionHook {

    @Override
    public boolean canBuild(Block block, Player player) {
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, block.getLocation(), "break", true);
    }


}
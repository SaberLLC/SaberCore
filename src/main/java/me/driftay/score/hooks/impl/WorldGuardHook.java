package me.driftay.score.hooks.impl;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.driftay.score.hooks.PluginHook;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class WorldGuardHook implements PluginHook<WorldGuardHook> {

    private static boolean instantiated = false;
    private WorldGuardPlugin worldGuardPlugin;

    public WorldGuardHook() {
        super();
    }

    @Override
    public WorldGuardHook setup() {
        this.worldGuardPlugin = WorldGuardPlugin.inst();
        instantiated = true;
        return this;
    }

    public boolean canBuild(Player player, Block block) {
        if (!instantiated) {
            return true;
        }
        return worldGuardPlugin.canBuild(player, block);
    }


    @Override
    public String getName() {
        return "WorldGuard";
    }

}

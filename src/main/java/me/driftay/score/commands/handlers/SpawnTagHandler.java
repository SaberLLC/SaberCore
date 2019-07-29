package me.driftay.score.commands.handlers;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import me.driftay.score.SaberCore;
import me.driftay.score.config.Config;
import me.driftay.score.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.Plugin;

import static com.sk89q.worldguard.bukkit.BukkitUtil.toVector;

@SuppressWarnings("Duplicates")
public class SpawnTagHandler implements Listener {

    private SaberCore plugin = SaberCore.getInstance();

    private static WorldGuardPlugin worldGuard;

    public static void hook() {
        Plugin worldGuardPlugin = Bukkit.getPluginManager().getPlugin("WorldGuard");

        if(worldGuardPlugin == null || !(worldGuardPlugin instanceof WorldGuardPlugin)) {
            worldGuard = null;
        } else {
            worldGuard = (WorldGuardPlugin) worldGuardPlugin;
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Player) {
                Player victim = (Player) event.getEntity();
                Player damager = (Player) event.getDamager();

                Faction victimFaction = FPlayers.getInstance().getByPlayer(victim).getFaction();
                Faction damagerFaction = FPlayers.getInstance().getByPlayer(damager).getFaction();

                if (victimFaction.isPeaceful() || damagerFaction.isPeaceful()) return;
                if (victimFaction.equals(damagerFaction) && !victimFaction.isWilderness()) return;
                if (!isPvPEnabled(victim) || !isPvPEnabled(damager)) return;
                //if(FactionsManager.getInstance().getVanishHandler().isVanished(victim) || FactionsManager.getInstance().getVanishHandler().isVanished(damager)) return;
                if (victimFaction.getRelationTo(damagerFaction) == Relation.ALLY) return;

                plugin.getPlayerManager().applyTagger(damager, victim);
                plugin.getPlayerManager().applyOther(damager, victim);

            } else if (event.getDamager() instanceof Projectile) {

                Projectile projectile = (Projectile) event.getDamager();

                if (projectile.getShooter() instanceof Player) {

                    Player shooter = (Player) projectile.getShooter();

                    if (shooter != event.getEntity()) {
                        Player player = (Player) event.getEntity();

                        Faction playerFaction = FPlayers.getInstance().getByPlayer(player).getFaction();
                        Faction shooterFaction = FPlayers.getInstance().getByPlayer(shooter).getFaction();

                        if (playerFaction.isPeaceful() || shooterFaction.isPeaceful()) return;
                        if (playerFaction.equals(shooterFaction) && !playerFaction.isWilderness()) return;
                        if (!isPvPEnabled(player) || !isPvPEnabled(shooter)) return;
                        //if(FactionsManager.getInstance().getVanishHandler().isVanished(player) || FactionsManager.getInstance().getVanishHandler().isVanished(shooter)) return;
                        if (playerFaction.getRelationTo(shooterFaction) == Relation.ALLY) return;

                        plugin.getPlayerManager().applyTagger(shooter, player);
                        plugin.getPlayerManager().applyOther(shooter, player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (plugin.getPlayerManager().isSpawnTagActive(player)) {
            player.setAllowFlight(false);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (plugin.getPlayerManager().isSpawnTagActive(player)) {
            boolean sendMessage = false;

            for (String command : Config.combatTagDisabledCommands) {

                if (event.getMessage().toLowerCase().startsWith("/" + command.toLowerCase())) {
                    event.setCancelled(true);
                    sendMessage = true;
                }
            }
            if (sendMessage) {
                player.sendMessage(Message.SPAWN_TAG_NO_COMMAND.getMessage());
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (PlayerData.tagged.containsKey(player.getUniqueId())) {
            PlayerData.tagged.remove(player.getUniqueId());
        }
    }

    public static boolean isPvPEnabled(Player player) {
        Location location = player.getLocation();
        World world = location.getWorld();
        Vector vector = toVector(location);
        RegionManager regionManager = worldGuard.getRegionManager(world);
        ApplicableRegionSet region = regionManager.getApplicableRegions(vector);

        return region.allows(DefaultFlag.PVP) || region.getFlag(DefaultFlag.PVP) == null;
    }
}


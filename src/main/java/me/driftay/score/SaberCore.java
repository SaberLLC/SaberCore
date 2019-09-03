package me.driftay.score;

import me.driftay.score.commands.*;
import me.driftay.score.commands.handlers.ChunkbusterListener;
import me.driftay.score.commands.handlers.HarvesterHoeListener;
import me.driftay.score.config.Conf;
import me.driftay.score.config.Persist;
import me.driftay.score.exempt.*;
import me.driftay.score.file.CustomFile;
import me.driftay.score.file.impl.MessageFile;
import me.driftay.score.hooks.HookManager;
import me.driftay.score.hooks.PluginHook;
import me.driftay.score.hooks.impl.FactionHook;
import me.driftay.score.hooks.impl.WorldGuardHook;
import me.driftay.score.listeners.InstaBreakSponge;
import me.driftay.score.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SaberCore extends JavaPlugin {

    public static SaberCore instance;
    private static Logger logger;
    private Persist persist;

    public static void log(String message) {
        logger.log(Level.INFO, message);
    }


    public static void log(Level level, String message) {
        logger.log(level, message);
    }


    public static SaberCore getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        logger = this.getLogger();
        registerListeners();
        registerBooleans();
        persist = new Persist();
        getDataFolder().mkdirs();
        Conf.load();
        Collections.singletonList(new MessageFile()).forEach(CustomFile::init);
        registerCommands();
        Util.register();
        List<PluginHook<?>> hooks = new ArrayList<>();
        hooks.add(new FactionHook());
        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            hooks.add(new WorldGuardHook());
        }
        new HookManager(hooks);
    }

    public void onDisable() {
        Conf.save();
    }

    private void registerListeners() {
        Util.getClassesInPackage(this, "me.driftay.score.listeners").stream().filter(Listener.class::isAssignableFrom).forEach(clazz -> {
            try {
                Bukkit.getPluginManager().registerEvents((Listener) clazz.newInstance(), this);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void registerCommands() {
        getCommand("lff").setExecutor(new CmdLFF());
        getCommand("jellylegs").setExecutor(new CmdJellyLegs());
        getCommand("anvil").setExecutor(new CmdAnvil());
        getCommand("nv").setExecutor(new CmdNightVision());
        getCommand("recycle").setExecutor(new CmdRecycle());
        getCommand("sabercore").setExecutor(new CmdReload());
        getCommand("ping").setExecutor(new CmdPing());
        getCommand("setslots").setExecutor(new CmdSetSlots(this));
    }


    private void registerBooleans() {
        if(Conf.useOreTracker){
            getServer().getPluginManager().registerEvents(new OreTracker(), this);
        }
        if(Conf.instaBreakSponges){
            getServer().getPluginManager().registerEvents(new InstaBreakSponge(), this);
        }
        if(Conf.denyPistonGlitching){
            getServer().getPluginManager().registerEvents(new PistonGlitching(), this);
        }
        if(Conf.denyBlazeWaterDamage){
            getServer().getPluginManager().registerEvents(new WaterProofBlazes(), this);
        }
        if(Conf.denyWeatherChanging){
            getServer().getPluginManager().registerEvents(new WeatherChange(), this);
        }
        if(Conf.denyNaturalMobSpawning){
            getServer().getPluginManager().registerEvents(new NaturalMobSpawning(), this);
        }
        if(Conf.useHarvesterHoes){
            getServer().getPluginManager().registerEvents(new HarvesterHoeListener(), this);
            getCommand("harvesterhoe").setExecutor(new CmdHarvesterHoe());
        }
        if(Conf.useChunkBusters){
            getServer().getPluginManager().registerEvents(new ChunkbusterListener(), this);
            getCommand("chunkbuster").setExecutor(new CmdChunkbuster());
        }
        if(Conf.cancelDragonEggTeleport){
            getServer().getPluginManager().registerEvents(new DragonEggAntiTP(), this);
        }
        if(Conf.useAntiDestroySystem){
            getServer().getPluginManager().registerEvents(new AntiDestroy(), this);
        }
        if(Conf.useAutoRespawn){
            getServer().getPluginManager().registerEvents(new AutoRespawn(), this);
        }
        if (Conf.useAntiCobbleMonster) {
            getServer().getPluginManager().registerEvents(new AntiCobbleMonster(), this);
        }
        if (Conf.useAntiWildernessSpawner) {
            getServer().getPluginManager().registerEvents(new AntiWildernessSpawner(), this);
        }
        if (Conf.useDisabledCommands) {
            getServer().getPluginManager().registerEvents(new DisabledCommands(), this);
        }
        if (Conf.useAntiBoatPlacement) {
            getServer().getPluginManager().registerEvents(new BoatListener(), this);
        }
        if (Conf.useSpawnerSponge) {
            getServer().getPluginManager().registerEvents(new SpawnerSponge(), this);
        }
        if (Conf.useRegionListener) {
            getServer().getPluginManager().registerEvents(new RegionListener(), this);
        }
        if (Conf.useBookDisenchant) {
            getServer().getPluginManager().registerEvents(new BookDisenchant(), this);
        }
        if(Conf.useAntiSpawnerMine){
            getServer().getPluginManager().registerEvents(new SpawnerMine(), this);
        }
        if(Conf.useStatTrackSword){
            getServer().getPluginManager().registerEvents(new StatTrackSwords(), this);
        }
    }
    public Persist getPersist() {
        return persist;
    }
}

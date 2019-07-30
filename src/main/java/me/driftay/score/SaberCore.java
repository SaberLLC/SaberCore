package me.driftay.score;

import me.driftay.score.commands.*;
import me.driftay.score.config.Config;
import me.driftay.score.config.Persist;
import me.driftay.score.exempt.*;
import me.driftay.score.file.CustomFile;
import me.driftay.score.file.impl.MessageFile;
import me.driftay.score.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
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
        Config.load();
        Collections.singletonList(new MessageFile()).forEach(CustomFile::init);
        registerCommands();

    }

    public void onDisable() {
        Config.save();
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
    }

    private void registerBooleans() {
        if (Config.useAntiCobbleMonster) {
            getServer().getPluginManager().registerEvents(new AntiCobbleMonster(), this);
        }
        if (Config.useAntiWildernessSpawner) {
            getServer().getPluginManager().registerEvents(new AntiWildernessSpawner(), this);
        }
        if (Config.useDisabledCommands) {
            getServer().getPluginManager().registerEvents(new DisabledCommands(), this);
        }
        if (Config.useAntiBoatPlacement) {
            getServer().getPluginManager().registerEvents(new BoatListener(), this);
        }
        if (Config.useSpawnerSponge) {
            getServer().getPluginManager().registerEvents(new SpawnerSponge(), this);
        }
        if (Config.useRegionListener) {
            getServer().getPluginManager().registerEvents(new RegionListener(), this);
        }
        if (Config.useBookDisenchant) {
            getServer().getPluginManager().registerEvents(new BookDisenchant(), this);
        }
    }

    public Persist getPersist() {
        return persist;
    }
}

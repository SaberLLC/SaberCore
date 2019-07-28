package me.driftay.score;

import me.driftay.score.commands.*;
import me.driftay.score.commands.handlers.PlayerHandler;
import me.driftay.score.config.Config;
import me.driftay.score.config.Persist;
import me.driftay.score.file.CustomFile;
import me.driftay.score.file.impl.MessageFile;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SaberCore extends JavaPlugin {

    public static SaberCore instance;
    private static Logger logger;
    private Persist persist;

    public void onEnable(){
        instance = this;
        logger = this.getLogger();
        registerListeners();
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
        Listener[] listeners = {
                new PlayerHandler(),
        };
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerCommands() {
        getCommand("lff").setExecutor(new CmdLFF());
        getCommand("jellylegs").setExecutor(new CmdJellyLegs());
        getCommand("anvil").setExecutor(new CmdAnvil());
        getCommand("nv").setExecutor(new CmdNightVision());
        getCommand("recycle").setExecutor(new CmdRecycle());
    }


    public static void log(String message) { logger.log(Level.INFO, message); }

    public static void log(Level level, String message) { logger.log(level, message); }

    public static SaberCore getInstance(){ return instance; }

    public Persist getPersist() { return persist; }
}

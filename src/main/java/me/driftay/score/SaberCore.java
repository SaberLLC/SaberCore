package me.driftay.score;

import me.driftay.score.config.Config;
import me.driftay.score.config.Persist;
import me.driftay.score.file.CustomFile;
import me.driftay.score.file.impl.MessageFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaberCore extends JavaPlugin {

    public static SaberCore instance;
    private static Logger logger;
    private Persist persist;

    public void onEnable(){
        instance = this;
        getDataFolder().mkdirs();
        persist = new Persist();
        logger = this.getLogger();
        Collections.singletonList(new MessageFile()).forEach(CustomFile::init);
        Config.load();
    }

    public void onDisable(){
        Config.save();
    }

    public static void log(String message) {
        logger.log(Level.INFO, message);
    }

    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    public static SaberCore getInstance(){ return instance; }

    public Persist getPersist() {
        return persist;
    }
}

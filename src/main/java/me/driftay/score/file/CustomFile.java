package me.driftay.score.file;

import me.driftay.score.SaberCore;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class CustomFile {

    private YamlConfiguration config;
    private File file;
    private File configFile;

    public CustomFile(SaberCore instance, String parent, String name) {
        if (!instance.getDataFolder().exists()) {
            instance.getDataFolder().mkdir();
        }
        if (parent != null) {
            file = new File(instance.getDataFolder(), "/" + parent);
            if (!file.exists()) {
                file.mkdir();
            }
            configFile = new File(file, name + ".yml");
        } else {
            configFile = new File(name + ".yml");
        }
        try {
            configFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reloadConfig();
    }

    public abstract void init();

    public void onExit() {
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getConfigFile() {
        return configFile;
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}

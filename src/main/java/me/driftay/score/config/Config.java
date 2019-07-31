package me.driftay.score.config;

import me.driftay.score.SaberCore;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static int lffCooldownSeconds = 30;
    public static int enderPearlCooldown = 15;
    public static boolean useAntiCobbleMonster = true;
    public static boolean useAntiWildernessSpawner = true;
    public static boolean useAntiBoatPlacement = true;
    public static boolean useSpawnerSponge = true;
    public static int spawnerSpongeRadius = 2;
    public static boolean useRegionListener = true;
    public static boolean useDisabledCommands = true;
    public static boolean useBookDisenchant = true;
    public static boolean useAntiSpawnerMine = true;
    public static double spawnerMineRadius = 200.0;

    public static List<String> disabledCommands = new ArrayList<>();
    public static List<String> blockedRegions = new ArrayList<>();
    private static transient Config i = new Config();

    static {
        disabledCommands.add("/icanhasbukkit");
        disabledCommands.add("/version");
        disabledCommands.add("/ver");
        disabledCommands.add("/plugins");
        disabledCommands.add("/pl");
        disabledCommands.add("/?");

        blockedRegions.add("spawn");
        blockedRegions.add("someworldeditregionname");
    }


    public static void load() {
        SaberCore.getInstance().getPersist().loadOrSaveDefault(i, Config.class, "config");
    }

    public static void save() {
        SaberCore.getInstance().getPersist().save(i, "config");
    }

}

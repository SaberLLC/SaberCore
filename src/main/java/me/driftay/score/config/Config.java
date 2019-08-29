package me.driftay.score.config;

import me.driftay.score.SaberCore;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static int lffCooldownSeconds = 30;
    public static int enderPearlCooldown = 15;
    public static boolean useAntiCobbleMonster = true;
    public static boolean useAutoRespawn = true;
    public static boolean useAntiDestroySystem = true;
    public static boolean useAntiWildernessSpawner = true;
    public static boolean useAntiBoatPlacement = true;
    public static boolean useSpawnerSponge = true;
    public static boolean cancelDragonEggTeleport = false;
    public static int spawnerSpongeRadius = 2;
    public static boolean useRegionListener = true;
    public static boolean useDisabledCommands = true;
    public static boolean useBookDisenchant = true;
    public static boolean useAntiSpawnerMine = true;
    public static boolean useStatTrackSword = true;
    public static double spawnerMineRadius = 200.0;


    public static List<String> disabledCommands = new ArrayList<>();
    public static List<String> blockedRegions = new ArrayList<>();
    public static List<String> statTrackSwordsLore = new ArrayList<>();
    public static List<String> lavaBurnDeny = new ArrayList<>();
    public static List<String> explosionDeny = new ArrayList<>();
    public static List<String> lightningDeny = new ArrayList<>();

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

        statTrackSwordsLore.add("&6{date} &e{player} &fwas killed by &e{killer}");

        lavaBurnDeny.add("DIAMOND_HELMET");
        lavaBurnDeny.add("DIAMOND_CHESTPLATE");
        lavaBurnDeny.add("DIAMOND_LEGGINGS");
        lavaBurnDeny.add("DIAMOND_BOOTS");
        lavaBurnDeny.add("DIAMOND_SWORD");
        lavaBurnDeny.add("DIAMOND_AXE");
        lavaBurnDeny.add("DIAMOND_PICKAXE");
        lavaBurnDeny.add("MOB_SPAWNER");
        lavaBurnDeny.add("HOPPER");

        explosionDeny.add("DIAMOND_HELMET");
        explosionDeny.add("DIAMOND_CHESTPLATE");
        explosionDeny.add("DIAMOND_LEGGINGS");
        explosionDeny.add("DIAMOND_BOOTS");
        explosionDeny.add("DIAMOND_SWORD");
        explosionDeny.add("DIAMOND_AXE");
        explosionDeny.add("DIAMOND_PICKAXE");
        explosionDeny.add("MOB_SPAWNER");
        explosionDeny.add("HOPPER");

        lightningDeny.add("DIAMOND_HELMET");
        lightningDeny.add("DIAMOND_CHESTPLATE");
        lightningDeny.add("DIAMOND_LEGGINGS");
        lightningDeny.add("DIAMOND_BOOTS");
        lightningDeny.add("DIAMOND_SWORD");
        lightningDeny.add("DIAMOND_AXE");
        lightningDeny.add("DIAMOND_PICKAXE");
        lightningDeny.add("MOB_SPAWNER");
        lightningDeny.add("HOPPER");


    }


    public static void load() {
        SaberCore.getInstance().getPersist().loadOrSaveDefault(i, Config.class, "config");
    }

    public static void save() {
        SaberCore.getInstance().getPersist().save(i, "config");
    }

}

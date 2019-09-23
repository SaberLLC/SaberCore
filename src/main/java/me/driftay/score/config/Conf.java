package me.driftay.score.config;

import me.driftay.score.SaberCore;

import java.util.ArrayList;
import java.util.List;

public class Conf {

    public static int lffCooldownSeconds = 30;
    public static int enderPearlCooldown = 15;
    public static boolean useAntiCobbleMonster = true;
    public static boolean useAutoRespawn = true;
    public static boolean useAntiWildernessSpawner = true;
    public static boolean useAntiBoatPlacement = true;
    public static boolean denyPistonGlitching = true;
    public static boolean cancelDragonEggTeleport = false;
    public static boolean denyNaturalMobSpawning = true;
    public static boolean denyWeatherChanging = true;
    public static boolean denyBlazeWaterDamage = true;
    public static boolean useSpawnerSponge = true;
    public static int spawnerSpongeRadius = 2;
    public static boolean useBookDisenchant = true;
    public static boolean useAntiSpawnerMine = true;
    public static double spawnerMineRadius = 200.0;

    public static boolean useSaberPicks = true;
    public static String saberPickDisplayName = "&c&lSaber Pickaxe";
    public static List<String> saberPickLore = new ArrayList<>();
    public static int saberPickEfficiencyLevel = 5;
    public static int saberPickUnbreakingLevel = 10;
    static{
        saberPickLore.add("&f ");
        saberPickLore.add("&4&l* &cRadius: &f{radius} x {radius}");
        saberPickLore.add("&4&l* &cObtained from: &fstore.mystore.com");
        saberPickLore.add("&f ");
        saberPickLore.add("&7&o(( Tip: your &f&opickaxe&7&o will &a&oautomatically");
        saberPickLore.add("&7&oswitch depending on the &a&oterrain&7&o! ))");
    }

    public static boolean useChunkBusters = true;
    public static String chunkBusterDisplayName = "&cChunkBuster";
    public static List<String> chunkBusterLore = new ArrayList<>();
    public static int chunkBusterWarmup = 10;
    public static String chunkBusterHologramFormat = "&c&l[!] &7ChunkBuster";
    public static boolean chunkbusterAsyncMode = false;
    public static boolean chunkBusterHologram = true;
    static{
        chunkBusterLore.add("&7A &cpowerful &7piece of &calien tech");
        chunkBusterLore.add("&7Destroys the chunk it is placed in!");
    }

    public static boolean useHarvesterHoes = true;
    public static String harvesterHoeDisplayName = "&aHarvester Hoe";
    public static List<String> harvesterHoeLore = new ArrayList<>();
    public static boolean harvesterHoeAutoSell = true;
    public static double perCanePrice = 0.5;
    static{
        harvesterHoeLore.add("&7When Mined, Places Sugar Cane Directly,");
        harvesterHoeLore.add("&7Into Your Inventory!");
    }


    public static boolean useStatTrackSword = true;
    public static List<String> statTrackSwordsLore = new ArrayList<>();
    static {
        statTrackSwordsLore.add("&6{date} &e{player} &fwas killed by &e{killer}");
    }

    public static boolean useDisabledCommands = true;
    public static List<String> disabledCommands = new ArrayList<>();
    static{
        disabledCommands.add("/icanhasbukkit");
        disabledCommands.add("/version");
        disabledCommands.add("/ver");
        disabledCommands.add("/plugins");
        disabledCommands.add("/pl");
        disabledCommands.add("/?");
    }

    public static boolean useRegionListener = true;
    public static List<String> blockedRegions = new ArrayList<>();
    static{
        blockedRegions.add("spawn");
        blockedRegions.add("someworldeditregionname");
    }

    public static boolean useAntiDestroySystem = true;
    public static List<String> lavaBurnDeny = new ArrayList<>();
    public static List<String> explosionDeny = new ArrayList<>();
    public static List<String> lightningDeny = new ArrayList<>();
    static {
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

    private static transient Conf i = new Conf();

    public static void load() {
        SaberCore.getInstance().getPersist().loadOrSaveDefault(i, Conf.class, "config");
    }

    public static void save() {
        SaberCore.getInstance().getPersist().save(i, "config");
    }

}

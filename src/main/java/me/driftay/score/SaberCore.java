package me.driftay.score;

import me.driftay.score.commands.command.*;
import me.driftay.score.commands.command.chat.CmdMuteChat;
import me.driftay.score.commands.command.chat.CmdSlowChat;
import me.driftay.score.commands.handlers.ChunkbusterListener;
import me.driftay.score.commands.handlers.HarvesterHoeListener;
import me.driftay.score.commands.handlers.ShockwaveListener;
import me.driftay.score.config.Persist;
import me.driftay.score.exempt.*;
import me.driftay.score.exempt.mobs.*;
import me.driftay.score.file.CustomFile;
import me.driftay.score.file.impl.MessageFile;
import me.driftay.score.commands.command.chat.ChatHandler;
import me.driftay.score.commands.command.chat.ChatListener;
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
    public List<String> itemList = getConfig().getStringList("DeniedItemStorage.Items");
    private ChatHandler chatHandler;


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
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        System.out.println(startUpString());
        registerListeners();
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        registerBooleans();
        persist = new Persist();
        getDataFolder().mkdirs();
        this.chatHandler = new ChatHandler();
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
        instance = null;
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
        getCommand("playerinfo").setExecutor(new CmdPlayerInfo());
        getCommand("mutechat").setExecutor(new CmdMuteChat());
        getCommand("slowchat").setExecutor(new CmdSlowChat());
        getCommand("giveall").setExecutor(new CmdGiveAll());
    }


    private void registerBooleans() {
        if(getConfig().getBoolean("denyIronGolemsTargetZombies")){
            getServer().getPluginManager().registerEvents(new IronGolemAI(), this);
        }

        if(getConfig().getBoolean("Shockwave.Enabled")){
            getServer().getPluginManager().registerEvents(new ShockwaveListener(), this);
            getCommand("shockwave").setExecutor(new CmdShockwave());
        }

        if(getConfig().getBoolean("useOreTracker")){
            getServer().getPluginManager().registerEvents(new OreTracker(), this);
        }
        if(getConfig().getBoolean("instaSpongeBreak")){
            getServer().getPluginManager().registerEvents(new InstaBreakSponge(), this);
        }
        if(getConfig().getBoolean("denyPistonFarming")){
            getServer().getPluginManager().registerEvents(new PistonGlitching(), this);
        }
        if(getConfig().getBoolean("denyBlazeWaterDamage")){
            getServer().getPluginManager().registerEvents(new WaterProofBlazes(), this);
        }
        if(getConfig().getBoolean("denyNaturalMobSpawning")){
            getServer().getPluginManager().registerEvents(new NaturalMobSpawning(), this);
        }
        if(getConfig().getBoolean("HarvesterHoe.Enabled")){
            getServer().getPluginManager().registerEvents(new HarvesterHoeListener(), this);
            getCommand("harvesterhoe").setExecutor(new CmdHarvesterHoe());
        }
        if(getConfig().getBoolean("Chunkbuster.Enabled")){
            getServer().getPluginManager().registerEvents(new ChunkbusterListener(), this);
            getCommand("chunkbuster").setExecutor(new CmdChunkbuster());
        }
        if(getConfig().getBoolean("cancelDragonEggTeleport")){
            getServer().getPluginManager().registerEvents(new DragonEggAntiTP(), this);
        }
        if(getConfig().getBoolean("useAntiDestroySystem")){
            getServer().getPluginManager().registerEvents(new AntiDestroy(), this);
        }
        if(getConfig().getBoolean("useAutoRespawn")){
            getServer().getPluginManager().registerEvents(new AutoRespawn(), this);
        }
        if (getConfig().getBoolean("useAntiCobbleMonster")) {
            getServer().getPluginManager().registerEvents(new AntiCobbleMonster(), this);
        }
        if (getConfig().getBoolean("useAntiWildernessSpawner")) {
            getServer().getPluginManager().registerEvents(new AntiWildernessSpawner(), this);
        }
        if (getConfig().getBoolean("DisabledCommands.Enabled")) {
            getServer().getPluginManager().registerEvents(new DisabledCommands(), this);
        }
        if (getConfig().getBoolean("useAntiBoatPlacement")) {
            getServer().getPluginManager().registerEvents(new BoatListener(), this);
        }
        if (getConfig().getBoolean("SpawnerSponge.Enabled")) {
            getServer().getPluginManager().registerEvents(new SpawnerSponge(), this);
            getServer().getPluginManager().registerEvents(new SoakSponge(), this);
        }
        if (getConfig().getBoolean("useBookDisenchant")) {
            getServer().getPluginManager().registerEvents(new BookDisenchant(), this);
        }
        if(getConfig().getBoolean("AntiSpawnerMine.Enabled")){
            getServer().getPluginManager().registerEvents(new SpawnerMine(), this);
        }
        if(getConfig().getBoolean("StatTrack-Swords.Enabled")){
            getServer().getPluginManager().registerEvents(new StatTrackSwords(), this);
        }
        if(getConfig().getBoolean("useAntiMobTargeting")){
            getServer().getPluginManager().registerEvents(new AntiMobTargeting(), this);
        }
        if(getConfig().getBoolean("useAntiMobAI")){
            getServer().getPluginManager().registerEvents(new AntiMobMoving(), this);
        }
        if(getConfig().getBoolean("denyExplosionDamage")){
            getServer().getPluginManager().registerEvents(new DenyExplosionDamage(), this);
        }
        if(getConfig().getBoolean("reduceIronGolemHealth")){
            getServer().getPluginManager().registerEvents(new IronGolemHealth(), this);
        }
        if(getConfig().getBoolean("useAntiZombieBaby")){
            getServer().getPluginManager().registerEvents(new AntiBabyZombie(), this);
        }
    }
    public ChatHandler getChatHandler() { return chatHandler; }

    private static String startUpString(){
        return "\n" +
                "  ██████  ▄▄▄       ▄▄▄▄   ▓█████  ██▀███   ▄████▄   ▒█████   ██▀███  ▓█████ \n" +
                "▒██    ▒ ▒████▄    ▓█████▄ ▓█   ▀ ▓██ ▒ ██▒▒██▀ ▀█  ▒██▒  ██▒▓██ ▒ ██▒▓█   ▀ \n" +
                "░ ▓██▄   ▒██  ▀█▄  ▒██▒ ▄██▒███   ▓██ ░▄█ ▒▒▓█    ▄ ▒██░  ██▒▓██ ░▄█ ▒▒███   \n" +
                "  ▒   ██▒░██▄▄▄▄██ ▒██░█▀  ▒▓█  ▄ ▒██▀▀█▄  ▒▓▓▄ ▄██▒▒██   ██░▒██▀▀█▄  ▒▓█  ▄ \n" +
                "▒██████▒▒ ▓█   ▓██▒░▓█  ▀█▓░▒████▒░██▓ ▒██▒▒ ▓███▀ ░░ ████▓▒░░██▓ ▒██▒░▒████▒\n" +
                "▒ ▒▓▒ ▒ ░ ▒▒   ▓▒█░░▒▓███▀▒░░ ▒░ ░░ ▒▓ ░▒▓░░ ░▒ ▒  ░░ ▒░▒░▒░ ░ ▒▓ ░▒▓░░░ ▒░ ░\n" +
                "░ ░▒  ░ ░  ▒   ▒▒ ░▒░▒   ░  ░ ░  ░  ░▒ ░ ▒░  ░  ▒     ░ ▒ ▒░   ░▒ ░ ▒░ ░ ░  ░\n" +
                "░  ░  ░    ░   ▒    ░    ░    ░     ░░   ░ ░        ░ ░ ░ ▒    ░░   ░    ░   \n" +
                "      ░        ░  ░ ░         ░  ░   ░     ░ ░          ░ ░     ░        ░  ░\n" +
                "                         ░                 ░                                 \n";
    }

    public Persist getPersist() {
        return persist;
    }
}

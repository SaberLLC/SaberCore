package me.driftay.score.utils;

import org.bukkit.ChatColor;

public enum Message {

    NO_PERMISSION("no-permission", "&c&l[!] &7You do not have permission."),
    NOT_PLAYER("must-be-player", "&c&l[!] &7You must be a player to use this command!"),
    PLAYER_NOT_FOUND("player-not-found", "&c&l[!] &b%player% &7is not online!"),

    DISABLED_COMMAND_MESSAGE("disabled-commands-message", "&c&l[!] &4You are not permitted to use &c&l&n%command%"),

    PING_YOURSELF("ping-yourself", "&c&l[!] &7Your ping is &b%ping%ms&7."),
    PING_OTHER("ping-other", "&c&l[!] &b%player%'s &7ping is &b%ping%&7."),
    SLOW_CHAT_COOLDOWN("slowchat.cooldown", "&c&l[!] &7You can chat again in &b&l{time} &7seconds."),
    SLOW_CHAT_BROADCAST("slowchat.broadcast", "&cPublic chat has been slowed"),
    MUTE_CHAT_TOGGLED("mute-chat.toggled", "&ePublic Chat has been &d{context} &eby {player}"),
    ANTI_SPAWNER_MINE_PLAYERS_NEAR("anti-spawner-mine-players-near", "&c&l[!] &7You may not break spawners while enemies are near!"),
    POTS_STACKED("pots.stacked", "&2&l[!] &7All of the potions in your inventory have been stacked!"),

    GIVE_ALL_INVALID_ITEM("give-all.item-invalid", "&c&l[!] &7You can't give air to everyone xD"),
    GIVE_ALL_RECEIVED("give-all.received", "&2&l[!] &7Everyone on the server has been gifted &b{item}&7!"),
    GIVE_ALL_EXECUTED("give-all.executed", "&2&l[!] &7You have given everyone on the server &b{item}&7!"),

    ENDERPEARL_ON_COOLDOWN("enderpearl-on-cooldown", "&c&l[!] &7You cannot use another enderpearl for &b%time%&7!"),
    ANVIL_OPENED("anvil-open", "&c&l[!] &7Opening anvil..."),

    LFF_MESSAGE("lff.message", "&c&lLFF &7- &b%player% &7is looking for a faction! &f(Hover)"),
    LFF_COOLDOWN_MESSAGE("lff.cooldown-message", "&c&l[!] &7You cannot use &b/lff &7for another &b%seconds% &7seconds!"),

    //Chunkbusters
    CHUNKBUSTER_COMMAND_USAGE("chunkbuster.command-usage", "&c&l[!] &7Try /chunkbuster give <player> <amount>."),
    CHUNKBUSTER_RECEIVED_MESSAGE("chunkbuster.received", "&c&l[!] &7You have received a chunkbuster."),
    CHUNKBUSTER_ALREADY_BEING_BUSTED("chunkbuster.being-busted", "&c&l[!] &7This chunk is already being busted."),
    CHUNKBUSTER_USE_MESSAGE("chunkbuster.use-message", "&c&l[!] &7A ChunkBuster has been placed, you have &c10 seconds &7 to leave this chunk!"),
    CHUNKBUSTER_CANT_PLACE("chunkbuster.cant-place", "&c&l[!] &7You can't place ChunkBusters here!"),

    //HarvesterHoes
    HARVESTER_COMMAND_USAGE("harvester.command-usage", "&c&l[!] &7Try /harvesterhoe give <player> <amount>."),
    HARVESTER_RECEIVED_MESSAGE("harvester.received", "&c&l[!] &7You have received a harvester hoe."),

    RECYCLE_ON("recycle-on", "&c&l[!] &7You have togged recycling on!"),
    RECYCLE_OFF("recycle-off", "&c&l[!] &7You have togged recycling off!"),

    NIGHTVISION_ON("nightvision-on", "&c&l[!] &7You have togged night vision on!"),
    NIGHTVISION_OFF("nighvision-off", "&c&l[!] &7You have togged night vision off!"),

    JELLYLEGS_ON("jellylegs-on", "&c&l[!] &7You have togged jelly legs on!"),
    JELLYLEGS_OFF("jellylegs-off", "&c&l[!] &7You have togged jelly legs off!"),

    SHOCKWAVE_COMMAND_USAGE("shockwave.command-usage", "&c&l[!] &7Try /shockwave give <pickaxe/shovel/hoe> <radius> <player>"),
    SHOCKWAVE_RECEIVED_MESSAGE("shockwave.received", "&c&l[!] &7You have received a shockwave tool!"),

    CANNOT_STORE_ITEM("antistorage.cannot-store", "&c&l[!] &7You may not store &b{item}&7!"),

    // Death messages
    DM_PLAYER("deathmessages.player.without-weapon", "&c%victim% &fhas been killed by &b%killer%"),
    DM_PLAYER_WITH_WEAPON("deathmessages.player.with-weapon", "&c%victim% &fhas been killed by &b%killer% &fusing &r%weapon%"),

    DM_ZOMBIE_NORMAL("deathmessages.zombie.normal", "&c%victim% &fhas been killed by a Zombie"),
    DM_ZOMBIE_BABY("deathmessages.zombie.normal", "&c%victim% &fhas been killed by a baby Zombie"),
    DM_ZOMBIE_VILLAGER("deathmessages.zombie.villager", "&c%victim% &fhas been killed by a villager Zombie"),
    DM_SKELETON("deathmessages.skeleton", "&c%victim% &fhas been killed by a Skeleton"),
    DM_ENDERMAN("deathmessages.enderman", "&c%victim% &fhas been killed by an Enderman"),
    DM_BLAZE("deathmessages.blaze", "&c%victim% &fhas been killed by a Blaze"),
    DM_WITCH("deathmessages.witch", "&c%victim% &fhas been killed by an Witch"),
    DM_SPIDER("deathmessages.spider", "&c%victim% &fhas been killed by a Spider"),
    DM_CAVE_SPIDER("deathmessages.cave-spider", "&c%victim% &fhas been killed by a Cave Spider"),
    DM_SILVERFISH("deathmessages.silverfish", "&c%victim% &fhas been killed by a Silverfish"),
    DM_CREEPER_NORMAL("deathmessages.creeper.normal", "&c%victim% &fhas been killed by a Creeper"),
    DM_CREEPER_POWERED("deathmessages.creeper.powered", "&c%victim% &fhas been killed by a powered Creeper"),
    DM_GIANT("deathmessages.giant", "&c%victim% &fhas been killed by a Giant"),
    DM_GUARDIAN_NORMAL("deathmessages.guardian.normal", "&c%victim% &fhas been killed by a Guardian"),
    DM_GUARDIAN_ELDER("deathmessages.guardian.elder", "&c%victim% &fhas been killed by an Elder Guardian"),
    DM_OTHER("deathmessages.other", "&c%victim% &fhas been killed by a %mob_name%"),

    /*
     * Other
     */

    DM_FALL("deathmessages.fall", "&c%victim% &ffell from a high place"),
    DM_SUICIDE("deathmessages.suicide", "&c%victim% &fhas commit a suicide"),
    DM_DROWNED("deathmessages.drowned", "&c%victim% &fdrowned"),
    DM_SUFFOCATION("deathmessages.suffocation", "&c%victim% &fsuffocated in a wall"),
    DM_FIRE("deathmessages.fire", "&c%victim% &fburn to death"),
    DM_LAVA("deathmessages.lava", "&c%victim% &fburn to death in lava"),
    DM_VOID("deathmessages.void", "&c%victim% &ffell in the void"),
    DM_STARVATION("deathmessages.starvation", "&c%victim% &fstarved to death"),
    DM_POISON("deathmessages.poison", "&c%victim% &fhas been poisoned"),
    DM_MAGIC("deathmessages.magic", "&c%victim% &fkilled by magic"),
    DM_FALLING_BLOCK("deathmessages.falling-blocks", "&c%victim% &fhas been killed by falling blocks"),
    DM_TNT("deathmessages.primed-tnt", "&c%victim% &fhas been killed by a Primed TNT"),
    DM_FIREBALL("deathmessages.fire-ball", "&c%victim% &fhas been killed by a Fire Ball"),
    DM_LIGHTNING("deathmessages.lightning", "&c%victim% &fhas been killed by a Lightning Strike"),
    DM_THROWN_POTION("deathmessages.thrown-potion", "&c%victim% &fhas been killed by a Thrown Potion"),
    DM_WITHER("deathmessages.wither", "&c%victim% &fhas been killed by a Wither");

    String config, message;

    Message(String config, String message) {
        this.config = config;
        this.message = ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getConfig() {
        return config;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

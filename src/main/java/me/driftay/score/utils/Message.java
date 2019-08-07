package me.driftay.score.utils;

import org.bukkit.ChatColor;

public enum Message {

    NO_PERMISSION("no-permission", "&c&l[!] &7You do not have permission."),
    NOT_PLAYER("must-be-player", "&c&l[!] &7You must be a player to use this command!"),
    PLAYER_NOT_FOUND("player-not-found", "&c&l[!] &b%player% &7is not online!"),

    DISABLED_COMMAND_MESSAGE("disabled-commands-message", "&c&l[!] &4You are not permitted to use &c&l&n%command%"),

    PING_YOURSELF("ping-yourself", "&c&l[!] &7Your ping is &b%ping%ms&7."),
    PING_OTHER("ping-other", "&c&l[!] &b%player%'s &7ping is &b%ping%&7."),

    ANTI_SPAWNER_MINE_PLAYERS_NEAR("anti-spawner-mine-players-near", "&c&l[!] &7You may not break spawners while enemies are near!"),

    ENDERPEARL_ON_COOLDOWN("enderpearl-on-cooldown", "&c&l[!] &7You cannot use another enderpearl for &b%time%&7!"),
    ANVIL_OPENED("anvil-open", "&c&l[!] &7Opening anvil..."),

    LFF_MESSAGE("lff.message", "&c&lLFF &7- &b%player% &7is looking for a faction! &f(Hover)"),
    LFF_COOLDOWN_MESSAGE("lff.cooldown-message", "&c&l[!] &7You cannot use &b/lff &7for another &b%seconds% &7seconds!"),

    RECYCLE_ON("recycle-on", "&c&l[!] &7You have togged recycling on!"),
    RECYCLE_OFF("recycle-off", "&c&l[!] &7You have togged recycling off!"),

    NIGHTVISION_ON("nightvision-on", "&c&l[!] &7You have togged night vision on!"),
    NIGHTVISION_OFF("nighvision-off", "&c&l[!] &7You have togged night vision off!"),

    JELLYLEGS_ON("jellylegs-on", "&c&l[!] &7You have togged jelly legs on!"),
    JELLYLEGS_OFF("jellylegs-off", "&c&l[!] &7You have togged jelly legs off!");

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

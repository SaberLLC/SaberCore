package me.driftay.score.utils;

import me.driftay.score.SaberCore;
import org.bukkit.ChatColor;

public class Logger {

    public static void print(String message, PrefixType type) {
        SaberCore.instance.getServer().getConsoleSender().sendMessage(type.getPrefix() + message);
    }

    public enum PrefixType {

        WARNING(ChatColor.RED + "WARNING: "), NONE(""), DEFAULT(ChatColor.GOLD + "[SaberCore] "), FAILED(ChatColor.RED + "FAILED: ");

        private String prefix;

        PrefixType(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return this.prefix;
        }

    }

}

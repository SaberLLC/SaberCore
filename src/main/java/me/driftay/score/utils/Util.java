package me.driftay.score.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> color(List<String> string) {
        List<String> colored = new ArrayList<>();
        for (String line : string) {
            colored.add(color(line));
        }
        return colored;
    }

    public static void sendConsole(String command) {
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
    }
}

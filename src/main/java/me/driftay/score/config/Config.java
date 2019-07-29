package me.driftay.score.config;

import me.driftay.score.SaberCore;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private static transient Config i = new Config();

    public static int lffCooldownSeconds = 30;

    public static int enderPearlCooldown = 15;

    public static int combatTagTimer = 20;

    public static List<String> combatTagDisabledCommands = new ArrayList<>();
    static{
        combatTagDisabledCommands.add("f home");
        combatTagDisabledCommands.add("fhome");
        combatTagDisabledCommands.add("f stuck");
        combatTagDisabledCommands.add("fstuck");
        combatTagDisabledCommands.add("f logout");
    }

    public static void load() { SaberCore.getInstance().getPersist().loadOrSaveDefault(i, Config.class, "config"); }

    public static void save() { SaberCore.getInstance().getPersist().save(i, "config"); }

}

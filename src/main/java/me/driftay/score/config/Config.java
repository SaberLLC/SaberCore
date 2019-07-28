package me.driftay.score.config;

import me.driftay.score.SaberCore;

public class Config {

    private static transient Config i = new Config();

    public static int lffCooldownSeconds = 30;

    public static void load() { SaberCore.getInstance().getPersist().loadOrSaveDefault(i, Config.class, "config"); }

    public static void save() { SaberCore.getInstance().getPersist().save(i, "config"); }

}

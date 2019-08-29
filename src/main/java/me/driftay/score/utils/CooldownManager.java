package me.driftay.score.utils;

import me.driftay.score.config.Conf;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    public static int DEFAULT_COOLDOWN = Conf.lffCooldownSeconds;
    private Map<UUID, Integer> cooldowns = new HashMap<>();

    public void setCooldown(UUID player, int time) {
        if (time < 1) {
            cooldowns.remove(player);
        } else {
            cooldowns.put(player, time);
        }
    }

    public int getCooldown(UUID player) {
        return cooldowns.getOrDefault(player, 0);
    }
}

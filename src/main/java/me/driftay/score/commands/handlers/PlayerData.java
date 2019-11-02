package me.driftay.score.commands.handlers;

import me.driftay.score.utils.Util;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
public class PlayerData {

    public static Map<String, PlayerData> playerDataMap = new HashMap<>();
    public static ThreadLocal<DecimalFormat> remaining_seconds = ThreadLocal.withInitial(() -> new DecimalFormat("0.#"));
    public static ThreadLocal<DecimalFormat> remaining_seconds_trailing = ThreadLocal.withInitial(() -> new DecimalFormat("0.0"));
    private Map<UUID, Long> pearlCooldown = new HashMap<>();
    private String name;

    public PlayerData(String name) {
        this.name = name;

        playerDataMap.put(name, this);
    }

    public static String getRemaining(long duration, boolean milliseconds, boolean trail) {
        if (milliseconds && duration < TimeUnit.MINUTES.toMillis(1)) {
            return (trail ? remaining_seconds_trailing : remaining_seconds).get().format((double) duration * 0.001) + 's';
        }

        return DurationFormatUtils.formatDuration(duration, ((duration >= TimeUnit.HOURS.toMillis(1) ? "HH:" : "") + "mm:ss"));
    }

    public static PlayerData getByName(String name) {
        PlayerData data = playerDataMap.get(name);

        return data == null ? new PlayerData(name) : playerDataMap.get(name);
    }

    public boolean isPearlActive(Player player) {
        return pearlCooldown.containsKey(player.getUniqueId()) && System.currentTimeMillis() < pearlCooldown.get(player.getUniqueId());
    }

    public void cancelPearl(Player player) {
        pearlCooldown.remove(player.getUniqueId());
    }

    public void applyPearlCooldown(Player player) {
        pearlCooldown.put(player.getUniqueId(), System.currentTimeMillis() + (Util.config.getInt("enderPearlCooldown") * 1000));
    }

    public long getPearlMillisecondsLeft(Player player) {
        if (pearlCooldown.containsKey(player.getUniqueId())) {
            return Math.max(pearlCooldown.get(player.getUniqueId()) - System.currentTimeMillis(), 0L);
        }
        return 0L;
    }
}

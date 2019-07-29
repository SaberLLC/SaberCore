package me.driftay.score.commands.handlers;

import me.driftay.score.config.Config;
import me.driftay.score.utils.Message;
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
    private Map<UUID, Long> pearlCooldown = new HashMap<>();
    public static Map<UUID, Long> tagged = new HashMap<>();
    private String name;

    public PlayerData(String name){
        this.name = name;

        playerDataMap.put(name, this);
    }

    public boolean isSpawnTagActive(Player player) {
        return tagged.containsKey(player.getUniqueId()) && System.currentTimeMillis() < tagged.get(player.getUniqueId());
    }

    public void applyTagger(Player tagger, Player other) {
        if(!tagged.containsKey(tagger.getUniqueId())) {
            tagger.sendMessage(Message.SPAWN_TAG_TAGGER.getMessage().replace("%player%", other.getName()));
        }
        this.disableFly(tagger);
        tagged.put(tagger.getUniqueId(), System.currentTimeMillis() + (Config.combatTagTimer * 1000));
    }

    public void applyOther(Player tagger, Player other) {
        if(!tagged.containsKey(other.getUniqueId())) {
            other.sendMessage(Message.SPAWN_TAG_OTHER.getMessage().replace("%player%", tagger.getName()));
        }
        this.disableFly(other);
        tagged.put(other.getUniqueId(), System.currentTimeMillis() + (Config.combatTagTimer * 1000));
    }

    public long getSpawnTagMillisecondsLeft(Player player) {
        if(tagged.containsKey(player.getUniqueId())) {
            return Math.max(tagged.get(player.getUniqueId()) - System.currentTimeMillis(), 0L);
        }
        return 0L;
    }



    private void disableFly(Player player) {
        if(player.isFlying()) {
            player.setFlying(false);
        }
    }

    public boolean isPearlActive(Player player) {
        return pearlCooldown.containsKey(player.getUniqueId()) && System.currentTimeMillis() < pearlCooldown.get(player.getUniqueId());
    }

    public void cancelPearl(Player player) {
        pearlCooldown.remove(player.getUniqueId());
    }

    public void applyPearlCooldown(Player player) {
        pearlCooldown.put(player.getUniqueId(), System.currentTimeMillis() + (Config.enderPearlCooldown * 1000));
    }

    public long getPearlMillisecondsLeft(Player player) {
        if(pearlCooldown.containsKey(player.getUniqueId())) {
            return Math.max(pearlCooldown.get(player.getUniqueId()) - System.currentTimeMillis(), 0L);
        }
        return 0L;
    }

    public static ThreadLocal<DecimalFormat> remaining_seconds = ThreadLocal.withInitial(() -> new DecimalFormat("0.#"));

    public static ThreadLocal<DecimalFormat> remaining_seconds_trailing = ThreadLocal.withInitial(() -> new DecimalFormat("0.0"));

    public static String getRemaining(long duration, boolean milliseconds, boolean trail) {
        if(milliseconds && duration < TimeUnit.MINUTES.toMillis(1)) {
            return (trail ? remaining_seconds_trailing : remaining_seconds).get().format((double) duration * 0.001) + 's';
        }

        return DurationFormatUtils.formatDuration(duration, ((duration >= TimeUnit.HOURS.toMillis(1) ? "HH:" : "") + "mm:ss"));
    }

    public static PlayerData getByName(String name) {
        PlayerData data = playerDataMap.get(name);

        return data == null ? new PlayerData(name) : playerDataMap.get(name);
    }
}

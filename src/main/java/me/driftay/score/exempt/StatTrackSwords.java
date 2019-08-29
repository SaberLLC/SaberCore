package me.driftay.score.exempt;

import me.driftay.score.config.Conf;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static me.driftay.score.utils.Util.color;


public class StatTrackSwords implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Player killer = event.getEntity().getKiller();

        if (killer == null) {
            return;
        }

        ItemStack killItem = killer.getItemInHand();

        if (killItem == null || killItem.getType() != Material.DIAMOND_SWORD) {
            return;
        }

        List<String> lore = new ArrayList<>();
        for (String string : Conf.statTrackSwordsLore) {
            if (killItem.hasItemMeta() && killItem.getItemMeta().hasLore()) {
                if (killItem.getItemMeta().getLore().size() > 10) {
                    return;
                }
                lore.addAll(killItem.getItemMeta().getLore());
            }
            lore.add(color(string.replace("{player}", player.getName())
                    .replace("{killer}", player.getKiller().getName())
                    .replace("{date}", new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(new Date()))));
        }

        ItemMeta itemMeta = killItem.getItemMeta();
        itemMeta.setLore(lore);
        killItem.setItemMeta(itemMeta);
    }
}


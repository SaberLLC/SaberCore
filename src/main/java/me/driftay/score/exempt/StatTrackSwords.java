package me.driftay.score.exempt;

import me.driftay.score.config.Config;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.driftay.score.utils.Util.color;


public class StatTrackSwords implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        try {
            final Player p = e.getEntity();
            if (p == null) return;
            final Player killer = p.getKiller();
            ItemStack item = killer.getItemInHand();
            if (item == null) return;
            ItemMeta im = item.getItemMeta();
            String name = im.getDisplayName();
            if (!(killer instanceof Player)) {
                return;
            }
            if (item.getType() != Material.DIAMOND_SWORD) {
                return;
            }
            List<String> lore = new ArrayList<>();
            for (String string : Config.statTrackSwordsLore) {
                if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
                    if (item.getItemMeta().getLore().size() > 10) {
                        return;
                    }
                    lore.addAll(item.getItemMeta().getLore());
                }
                lore.add(color(string.replace("{killer}", killer.getName()).replace("{player}", p.getName())));
            }
            //lore.add(ChatColor.YELLOW + killer.getName() + ChatColor.RED + " killed " + ChatColor.YELLOW + p.getName());
            im.setLore(lore);
            im.setDisplayName(name);
            item.setItemMeta(im);
        }catch(NullPointerException e1){

        }
    }
}


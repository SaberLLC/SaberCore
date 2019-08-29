package me.driftay.score.utils;

import me.driftay.score.config.Conf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemCreation {

    public static ItemStack giveChunkBuster(int amount) {
        ItemStack busterItem = new ItemStack(XMaterial.END_PORTAL_FRAME.parseMaterial());
        ItemMeta meta = busterItem.getItemMeta();
        meta.setDisplayName(Util.color(Conf.chunkBusterDisplayName));
        List<String> lore = new ArrayList<>();
        for (String s : Conf.chunkBusterLore) {
            lore.add(Util.color(s));
        }
        meta.setLore(lore);
        busterItem.setAmount(amount);
        busterItem.setItemMeta(meta);
        return busterItem;
    }

    public static ItemStack giveHarvesterHoe(int amount) {
        ItemStack harvesterItem = new ItemStack(XMaterial.DIAMOND_HOE.parseMaterial());
        ItemMeta meta = harvesterItem.getItemMeta();
        meta.setDisplayName(Util.color(Conf.harvesterHoeDisplayName));
        List<String> lore = new ArrayList<>();
        for (final String s : Conf.harvesterHoeLore) {
            lore.add(Util.color(s));
        }
        meta.setLore(lore);
        harvesterItem.setAmount(amount);
        harvesterItem.setItemMeta(meta);
        return harvesterItem;
    }
}

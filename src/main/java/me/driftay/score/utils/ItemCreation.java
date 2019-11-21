package me.driftay.score.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.driftay.score.utils.Util.color;

public class ItemCreation {

    public static ItemStack giveChunkBuster(int amount) {
        ItemStack busterItem = new ItemStack(XMaterial.END_PORTAL_FRAME.parseMaterial());
        ItemMeta meta = busterItem.getItemMeta();
        meta.setDisplayName(color(Util.config.getString("Chunkbuster.Item.DisplayName")));
        List<String> lore = new ArrayList<>();
        for (String s : Util.config.getStringList("Chunkbuster.Item.Lore")) {
            lore.add(color(s));
        }
        meta.setLore(lore);
        busterItem.setAmount(amount);
        busterItem.setItemMeta(meta);
        return busterItem;
    }

    public static List<String> replaceinList(List<String> list, String placeholder, String value) {
        List<String> replaced = new ArrayList<>();
        list.forEach(line -> replaced.add(line.replace(placeholder, value)));
        return replaced;
    }

    public static ItemStack giveHarvesterHoe(int amount) {
        ItemStack harvesterItem = new ItemStack(XMaterial.DIAMOND_HOE.parseMaterial());
        ItemMeta meta = harvesterItem.getItemMeta();
        meta.setDisplayName(color(Util.config.getString("HarvesterHoe.Item.DisplayName")));
        List<String> lore = new ArrayList<>();
        for (String s : Util.config.getStringList("HarvesterHoe.Item.Lore")) {
            lore.add(color(s));
        }
        meta.setLore(lore);
        harvesterItem.setAmount(amount);
        harvesterItem.setItemMeta(meta);
        return harvesterItem;
    }
}

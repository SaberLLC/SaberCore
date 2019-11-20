package me.driftay.score.utils;

import itemnbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
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

    public static ItemStack createSmeltWandItem(int durability) {
        ItemStack smeltWand = XMaterial.matchXMaterial(Util.config.getString("Smelt-Wand.Item.Type")).parseItem();
        if(Util.config.getBoolean("Smelt-Wand.Item.Glow")){
            smeltWand.addUnsafeEnchantment(Glow.getGlow(), 1);
        }
        ItemMeta smeltWandItemMeta = smeltWand.getItemMeta();
        smeltWandItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        smeltWandItemMeta.setDisplayName(color(Util.config.getString("Smelt-Wand.Item.Display-Name")));
        List<String> lore = new ArrayList<>();

        for (String s : Util.config.getStringList("Smelt-Wand.Item.Lore")) {
            lore.add(color(s).replace("{durability}", durability + ""));
        }
        smeltWandItemMeta.setLore(lore);
        smeltWandItemMeta.spigot().setUnbreakable(true);
        smeltWand.setItemMeta(smeltWandItemMeta);
        NBTItem smeltWandNBTItem = new NBTItem(smeltWand);
        smeltWandNBTItem.setBoolean("smeltwand", true);
        smeltWandNBTItem.setInteger("durability", durability);
        return smeltWandNBTItem.getItem();
    }

    public static ItemStack createCraftWandItem(int durability) {
        ItemStack craftWand = XMaterial.matchXMaterial(Util.config.getString("Craft-Wand.Item.Type")).parseItem();
        if(Util.config.getBoolean("Craft-Wand.Item.Glow")){
            craftWand.addUnsafeEnchantment(Glow.getGlow(), 1);
        }
        ItemMeta craftWandItemMeta = craftWand.getItemMeta();
        craftWandItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        craftWandItemMeta.setDisplayName(color(Util.config.getString("Craft-Wand.Item.Display-Name")));
        List<String> lore = new ArrayList<>();

        for (String s : Util.config.getStringList("Craft-Wand.Item.Lore")) {
            lore.add(color(s).replace("{durability}", durability + ""));
        }
        craftWandItemMeta.setLore(lore);
        craftWandItemMeta.spigot().setUnbreakable(true);
        craftWand.setItemMeta(craftWandItemMeta);
        NBTItem craftWandNBTItem = new NBTItem(craftWand);
        craftWandNBTItem.setBoolean("craftwand", true);
        craftWandNBTItem.setInteger("durability", durability);
        return craftWandNBTItem.getItem();
    }

    public static ItemStack createSandWandItem(int durability) {
        ItemStack sandWand = XMaterial.matchXMaterial(Util.config.getString("Sand-Wand.Item.Type")).parseItem();
        if(Util.config.getBoolean("Sand-Wand.Item.Glow")){
            sandWand.addUnsafeEnchantment(Glow.getGlow(), 1);
        }
        ItemMeta sandWandItemMeta = sandWand.getItemMeta();
        sandWandItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        sandWandItemMeta.setDisplayName(color(Util.config.getString("Sand-Wand.Item.Display-Name")));
        List<String> lore = new ArrayList<>();

        for (String s : Util.config.getStringList("Sand-Wand.Item.Lore")) {
            lore.add(color(s).replace("{durability}", durability + ""));
        }
        sandWandItemMeta.setLore(lore);
        sandWandItemMeta.spigot().setUnbreakable(true);
        sandWand.setItemMeta(sandWandItemMeta);
        NBTItem sandWandNBTItem = new NBTItem(sandWand);
        sandWandNBTItem.setBoolean("sandwand", true);
        sandWandNBTItem.setInteger("durability", durability);
        return sandWandNBTItem.getItem();
    }

    public static ItemStack createLightningWandItem(int durability) {
        ItemStack lightningWand = XMaterial.matchXMaterial(Util.config.getString("Lightning-Wand.Item.Type")).parseItem();
        if(Util.config.getBoolean("Lightning-Wand.Item.Glow")){
            lightningWand.addUnsafeEnchantment(Glow.getGlow(), 1);
        }
        ItemMeta lightningWandItemMeta = lightningWand.getItemMeta();
        lightningWandItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        lightningWandItemMeta.setDisplayName(color(Util.config.getString("Lightning-Wand.Item.Display-Name")));
        List<String> lore = new ArrayList<>();

        for (String s : Util.config.getStringList("Lightning-Wand.Item.Lore")) {
            lore.add(color(s).replace("{durability}", durability + ""));
        }
        lightningWandItemMeta.setLore(lore);
        lightningWandItemMeta.spigot().setUnbreakable(true);
        lightningWand.setItemMeta(lightningWandItemMeta);
        NBTItem lightningWandNBTItem = new NBTItem(lightningWand);
        lightningWandNBTItem.setBoolean("lightningwand", true);
        lightningWandNBTItem.setInteger("durability", durability);
        return lightningWandNBTItem.getItem();
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

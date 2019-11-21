package me.driftay.score.commands.handlers.wands.impl;

import itemnbtapi.NBTItem;
import me.driftay.score.SaberCore;
import me.driftay.score.commands.handlers.wands.Wand;
import me.driftay.score.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.HashMap;

public class SmeltWand extends Wand {
    public HashMap<MaterialData, MaterialData> itemTable;

    public SmeltWand(ItemStack wandItemStack, Player player, Chest chest) {
        this.wandItemStack = wandItemStack;
        this.player = player;
        this.chest = chest;
        (this.itemTable = new HashMap<>()).put(new MaterialData(Material.IRON_ORE), new MaterialData(Material.IRON_INGOT));
        this.itemTable.put(new MaterialData(Material.GOLD_ORE), new MaterialData(Material.GOLD_INGOT));
        this.itemTable.put(new MaterialData(Material.SAND), new MaterialData(Material.GLASS));
        this.itemTable.put(new MaterialData(XMaterial.RED_SAND.parseMaterial()), new MaterialData(Material.GLASS));
    }

    public static ItemStack buildItem(int uses) {
        ItemStack itemStack = Wand.buildItem(XMaterial.matchXMaterial(Util.config.getString("Smelt-Wand.Item.Type")).parseMaterial());
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("Smelt", true);
        nbtItem.setInteger("Uses", uses);
        itemStack = nbtItem.getItem();
        if (Util.config.getBoolean("Smelt-Wand.Item.Glow")) {
            itemStack.addUnsafeEnchantment(Glow.getGlow(), 1);
        }
        return new ItemBuilder(itemStack)
                .name(Util.color(Util.config.getString("Smelt-Wand.Item.Display-Name")))
                .lore(Util.colorWithPlaceholders(Util.config.getStringList("Smelt-Wand.Item.Lore")
                        , new Placeholder("{uses}", uses + "")))
                .build();
    }

    public static boolean isSmeltWand(ItemStack itemStack) {
        if (!Wand.isWand(itemStack)) {
            return false;
        }
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey("Smelt");
    }

    public void run() {
        Bukkit.getScheduler().runTaskAsynchronously(SaberCore.instance, () -> {
            Inventory inventory = chest.getInventory();
            int blocksCondensed = condense(inventory);
            if (blocksCondensed == 0) {
                wandUsed = false;
                updateWand();
                return;
            }
            wandUsed = true;
            player.sendMessage(Message.WAND_SMELTED_ITEMS.getMessage().replace("{amount}", blocksCondensed + ""));
            updateWand();

    });
}
    private int condense(Inventory inventory) {
        int condenses = 0;
        int blocksCondensed = 0;
        ItemStack[] contents = inventory.getContents();
        for (int slot = 0; slot < contents.length; ++slot) {
            ItemStack item = contents[slot];
            if (item != null) {
                if (item.getType() != Material.AIR) {
                    MaterialData convertTo = itemTable.get(item.getData());
                    if (convertTo != null) {
                        int timesToConvert = item.getAmount();
                        blocksCondensed += timesToConvert;
                        if (timesToConvert != 0) {
                            ++condenses;
                            inventory.setItem(slot, null);
                            inventory.addItem(convertTo.toItemStack(timesToConvert));
                        }
                    }
                }
            }
        }
        if (condenses <= 0) {
            return blocksCondensed;
        }
        return blocksCondensed + condense(inventory);
    }

}

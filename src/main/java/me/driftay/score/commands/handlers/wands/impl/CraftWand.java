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

public class CraftWand extends Wand {

    public HashMap<MaterialData, MaterialData> itemTable;

    public CraftWand(ItemStack wandItemStack, Player player, Chest chest) {
        this.wandItemStack = wandItemStack;
        this.player = player;
        this.chest = chest;
        (this.itemTable = new HashMap<>()).put(new MaterialData(Material.EMERALD), new MaterialData(Material.EMERALD_BLOCK));
        this.itemTable.put(new MaterialData(Material.DIAMOND), new MaterialData(Material.DIAMOND_BLOCK));
        this.itemTable.put(new MaterialData(Material.IRON_INGOT), new MaterialData(Material.IRON_BLOCK));
        this.itemTable.put(new MaterialData(Material.GOLD_INGOT), new MaterialData(Material.GOLD_BLOCK));
        this.itemTable.put(new MaterialData(Material.COAL), new MaterialData(Material.COAL_BLOCK));
        this.itemTable.put(new MaterialData(Material.REDSTONE), new MaterialData(Material.REDSTONE_BLOCK));
        this.itemTable.put(new MaterialData(Material.INK_SACK, (byte) 4), new MaterialData(Material.LAPIS_BLOCK));
    }

    public static ItemStack buildItem(int uses) {
        ItemStack itemStack = Wand.buildItem(XMaterial.matchXMaterial(Util.config.getString("Craft-Wand.Item.Type")).parseMaterial());
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("Craft", true);
        nbtItem.setInteger("Uses", uses);
        itemStack = nbtItem.getItem();
        if (Util.config.getBoolean("Craft-Wand.Item.Glow")) {
            itemStack.addUnsafeEnchantment(Glow.getGlow(), 1);
        }
        return new ItemBuilder(itemStack)
                .name(Util.color(Util.config.getString("Craft-Wand.Item.Display-Name")))
                .lore(Util.colorWithPlaceholders(Util.config.getStringList("Craft-Wand.Item.Lore")
                        , new Placeholder("{uses}", uses + "")))
                .build();
    }

    public static boolean isCraftWand(ItemStack itemStack) {
        if (!Wand.isWand(itemStack)) {
            return false;
        }
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey("Craft");
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
            player.sendMessage(Message.WAND_CRAFTED_ITEMS.getMessage().replace("{amount}", blocksCondensed + ""));
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
                        int timesToConvert = item.getAmount() / 9;
                        blocksCondensed += timesToConvert;
                        int amountToKeep = item.getAmount() % 9;
                        if (timesToConvert != 0) {
                            ++condenses;
                            inventory.setItem(slot, null);
                            inventory.addItem(convertTo.toItemStack(timesToConvert));
                            if (amountToKeep > 0) {
                                item.setAmount(amountToKeep);
                                inventory.addItem(item);
                            }
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

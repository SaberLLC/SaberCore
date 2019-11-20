package me.driftay.score.commands.handlers.wands.impl;

import itemnbtapi.NBTItem;
import me.driftay.score.commands.handlers.wands.Wand;
import me.driftay.score.utils.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CraftWand extends Wand {

    public CraftWand(ItemStack wandItemStack, Player player, Chest chest) {
        this.wandItemStack = wandItemStack;
        this.player = player;
        this.chest = chest;
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



    }
}

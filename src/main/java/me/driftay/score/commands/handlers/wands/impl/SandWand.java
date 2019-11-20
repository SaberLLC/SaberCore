package me.driftay.score.commands.handlers.wands.impl;


import itemnbtapi.NBTItem;
import me.driftay.score.commands.handlers.wands.Wand;
import me.driftay.score.utils.*;
import me.driftay.score.utils.Particles.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SandWand extends Wand {

    public static List<String> sandWandItemsToRemove = Util.config.getStringList("Sand-Wand.Removable-Blocks");
    private Block block;

    public SandWand(ItemStack wandItemStack, Player player, Block block) {
        this.wandItemStack = wandItemStack;
        this.player = player;
        this.block = block;
    }

    public static ItemStack buildItem(int uses) {
        ItemStack itemStack = Wand.buildItem(XMaterial.matchXMaterial(Util.config.getString("Sand-Wand.Item.Type")).parseMaterial());
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("Sand", true);
        nbtItem.setInteger("Uses", uses);
        itemStack = nbtItem.getItem();
        if(Util.config.getBoolean("Sand-Wand.Item.Glow")){
            itemStack.addUnsafeEnchantment(Glow.getGlow(), 1);
        }
        return new ItemBuilder(itemStack)
                .name(Util.color(Util.config.getString("Sand-Wand.Item.Display-Name")))
                .lore(Util.colorWithPlaceholders(Util.config.getStringList("Sand-Wand.Item.Lore")
                        , new Placeholder("{uses}", uses + "")))
                .build();
    }

    public static boolean isSandWand(ItemStack itemStack) {
        if (!Wand.isWand(itemStack)) {
            return false;
        }
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey("Sand");
    }

    public void run() {
        if (!sandWandItemsToRemove.contains(block.getType().toString())) {
            player.sendMessage(Message.WAND_WAND_BLOCK_NOT_USABLE.getMessage());
            return;
        }
        ArrayList<Block> validBlocks = new ArrayList<>();
        validBlocks.add(block);
        Block loopBlock = block.getLocation().add(0, 1, 0).getBlock();
        validBlocks.add(loopBlock);
        int maxY = 1;
        while (sandWandItemsToRemove.contains(loopBlock.getType().toString())) {
            maxY++;
            loopBlock = loopBlock.getLocation().add(0, 1, 0).getBlock();
            validBlocks.add(loopBlock);
        }
        if (validBlocks.size() > 0) {
            wandUsed = true;
        }
        Location location = block.getLocation();
        showHelixAtLocation(location, maxY);
        for (Block removalBlock : validBlocks) {
            removalBlock.setType(Material.AIR);
        }
        updateWand();
    }

    private void showHelixAtLocation(Location location, int maxY) {
        int radius = 1;
        for (double y = 0; y <= maxY; y += 0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            ParticleEffect.REDSTONE
                    .display(new ParticleEffect.OrdinaryColor(255, 255, 255)
                            , new Location(location.getWorld(), location.getX() + x, location.getY() + y, location.getZ() + z), location.getWorld().getPlayers());
        }
    }
}

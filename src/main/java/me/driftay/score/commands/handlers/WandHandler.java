package me.driftay.score.commands.handlers;

import com.massivecraft.factions.*;
import me.driftay.score.SaberCore;
import me.driftay.score.commands.handlers.wands.Wand;
import me.driftay.score.commands.handlers.wands.impl.CraftWand;
import me.driftay.score.commands.handlers.wands.impl.LightningWand;
import me.driftay.score.commands.handlers.wands.impl.SandWand;
import me.driftay.score.utils.Message;
import me.driftay.score.utils.Util;
import me.driftay.score.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WandHandler implements Listener {

    @EventHandler
    public void onWandUse(PlayerInteractEvent e){
        if (e.getClickedBlock() == null
                || (!SandWand.sandWandItemsToRemove.contains(e.getClickedBlock().getType().toString())
                && e.getClickedBlock().getType() != XMaterial.CHEST.parseMaterial()
                && e.getClickedBlock().getType() != XMaterial.TRAPPED_CHEST.parseMaterial())
                || e.getItem() == null
                || !Wand.isWand(e.getItem())) {
            return;
        }

        Player player = e.getPlayer();
        Faction faction = FPlayers.getInstance().getByPlayer(player).getFaction();
        Faction wilderness = Factions.getInstance().getWilderness();
        ItemStack eventItem = e.getItem();
        Block block = e.getClickedBlock();
        Chest chest = (Chest) block.getState();


        Faction factionAt = (Board.getInstance().getFactionAt(new FLocation(e.getClickedBlock().getLocation())));
        if (!factionAt.equals(wilderness) && !factionAt.equals(faction)) {
            player.sendMessage(Message.WAND_CANNOT_USE_HERE.getMessage());
            return;
        }

        if (chest != null && Util.isEmpty(chest.getInventory())) {
            player.sendMessage(Message.WAND_CHEST_EMPTY.getMessage());
            e.setCancelled(true);
            return;
        }
        e.setCancelled(true);

        if (SandWand.isSandWand(eventItem)) {
            SandWand sandWand = new SandWand(eventItem, player, block);
            sandWand.takeWand();
            sandWand.run();
        }
    }

    @EventHandler
    public void click(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();

        Player p = e.getPlayer();
        if (block.getState() instanceof Chest) {
            Chest chest = (Chest) block.getState();
            if (chest == null) return;
            Inventory i1 = chest.getInventory();
            Bukkit.getScheduler().runTaskAsynchronously(SaberCore.instance, () -> {
                List<ItemStack> addBack = new ArrayList<>();

                if (CraftWand.isCraftWand(e.getItem())) {
                    CraftWand craftWand = new CraftWand(e.getItem(), p, chest);
                    craftWand.takeWand();
                }
                for (ItemStack item : i1.getContents()) {
                    if (item == null) continue;
                    int am = item.getAmount();
                    if (am >= 9) {
                        //can divide...
                        int blocks = am / 9; // how many actual blocks we can condense...
                        int remainder = am - (blocks * 9); // how many is left from this one ItemStack instnace...
                        Material blockMaterial = ingotToOre(item.getType());
                        Material remainderMaterial = item.getType();
                        if(blockMaterial == null) return;
                        if(remainderMaterial == null) return;
                        ItemStack goodBlock = new ItemStack(blockMaterial, blocks);
                        ItemStack goodRemainder = new ItemStack(remainderMaterial, remainder);
                        addBack.add(goodBlock);
                        addBack.add(goodRemainder);
                        chest.getInventory().removeItem(item);
                    }
                }
                for (ItemStack item : addBack) {
                    chest.getInventory().addItem(item);
                }
            });
        }
    }

    @EventHandler
    public void onLightning(PlayerInteractAtEntityEvent e){
        if(e.getRightClicked() == null
                || e.getRightClicked().getType() != EntityType.CREEPER
                || e.getPlayer().getItemInHand() == null) return;

        Creeper creeper = (Creeper) e.getRightClicked();

        if(creeper.isPowered()) {
            e.setCancelled(true);
            return;
        }

        Faction factionAt = (Board.getInstance().getFactionAt(new FLocation(e.getRightClicked().getLocation())));

        if(factionAt.isWarZone() || factionAt.isSafeZone()){
            e.setCancelled(true);
            e.getPlayer().sendMessage(Message.WAND_CANNOT_USE_HERE.getMessage());
            return;
        }
        ItemStack eventItem = e.getPlayer().getItemInHand();
        if(LightningWand.isLightningWand(eventItem)){
            LightningWand lightningWand = new LightningWand(eventItem, e.getPlayer(), e.getRightClicked());
            lightningWand.takeWand();
            lightningWand.run();
        }
    }

    public Material ingotToOre(Material i){
        if (i.equals(Material.IRON_INGOT)) return Material.IRON_BLOCK;
        if (i.equals(Material.IRON_INGOT)) return Material.IRON_BLOCK;
        if (i.equals(Material.IRON_INGOT)) return Material.IRON_BLOCK;
        if (i.equals(Material.IRON_INGOT)) return Material.IRON_BLOCK;

        return null;
    }
}

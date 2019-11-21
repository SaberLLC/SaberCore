package me.driftay.score.commands.handlers;

import com.massivecraft.factions.*;
import me.driftay.score.commands.handlers.wands.Wand;
import me.driftay.score.commands.handlers.wands.impl.CraftWand;
import me.driftay.score.commands.handlers.wands.impl.LightningWand;
import me.driftay.score.commands.handlers.wands.impl.SandWand;
import me.driftay.score.commands.handlers.wands.impl.SmeltWand;
import me.driftay.score.utils.Message;
import me.driftay.score.utils.XMaterial;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
        Chest chest = null;
        if (block.getState() instanceof Chest) {
            chest = (Chest) block.getState();
        }


        Faction factionAt = (Board.getInstance().getFactionAt(new FLocation(e.getClickedBlock().getLocation())));
        if (!factionAt.equals(wilderness) && !factionAt.equals(faction)) {
            player.sendMessage(Message.WAND_CANNOT_USE_HERE.getMessage());
            return;
        }

        e.setCancelled(true);
        if (CraftWand.isCraftWand(eventItem)) {
            CraftWand craftWand = new CraftWand(eventItem, player, chest);
            craftWand.takeWand();
            craftWand.run();
        }

        if (SmeltWand.isSmeltWand(eventItem)) {
            SmeltWand smeltWand = new SmeltWand(eventItem, player, chest);
            smeltWand.takeWand();
            smeltWand.run();
        }

        if (SandWand.isSandWand(eventItem)) {
            SandWand sandWand = new SandWand(eventItem, player, block);
            sandWand.takeWand();
            sandWand.run();
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
}

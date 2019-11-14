package me.driftay.score.commands.command;

import com.google.common.collect.Maps;
import me.driftay.score.SaberCore;
import me.driftay.score.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CmdStackPotions implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage(Message.NOT_PLAYER.getMessage());
            return true;
        }

        if(!player.hasPermission("sabercore.potstack")){
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
            return true;
        }

        HashMap<Short, Integer> pots = Maps.newHashMap();
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                continue;
            }

            if (item.getType() == Material.POTION) {
                if (pots.get(item.getDurability()) == null) {
                    pots.put(item.getDurability(), item.getAmount());
                } else {
                    pots.put(item.getDurability(), pots.get(item.getDurability()) + item.getAmount());
                }

                player.getInventory().removeItem(item);
            }
        }

        for (Short type : pots.keySet()) {
            int amount = pots.get(type);

            while (amount > 64) {
                amount = amount - 64;
                ItemStack add = new ItemStack(Material.POTION, 64, type);
                player.getInventory().addItem(add);
                player.updateInventory();
            }

            ItemStack rest = new ItemStack(Material.POTION, amount, type);
            player.getInventory().addItem(rest);
            player.updateInventory();
        }

        player.sendMessage(Message.POTS_STACKED.getMessage());
        return false;
    }


    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        ItemStack stack = event.getItem();

        if (stack.getType() == Material.POTION && (stack.getAmount() > 1)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPotionSplash(PlayerInteractEvent event) {
        if (!event.hasItem()) {
            return;
        }

        ItemStack stack = event.getItem();

        if (stack.getType() == Material.POTION && (stack.getAmount() > 1)) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTask(SaberCore.instance, () -> event.getPlayer().updateInventory());
        }
    }
}

package me.driftay.score.commands.command;

import me.driftay.score.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CmdGiveAll implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("sabercore.giveall")) {
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
            return true;
        }
        ItemStack itemstack = player.getItemInHand();
        if (itemstack == null || itemstack.getType() == Material.AIR) {
            sender.sendMessage(Message.GIVE_ALL_INVALID_ITEM.getMessage());
            return true;
        }
        for (Player online : Bukkit.getOnlinePlayers()) {
            Map<Integer, ItemStack> drops = online.getInventory().addItem(itemstack);
            if (!drops.isEmpty()) {
                drops.values().forEach(value -> player.getWorld().dropItemNaturally(player.getLocation(), value));
            }
            online.sendMessage(Message.GIVE_ALL_RECEIVED.getMessage().replace("{item}", itemstack.getType().toString()));
        }
        sender.sendMessage(Message.GIVE_ALL_EXECUTED.getMessage());

        return false;
    }
}

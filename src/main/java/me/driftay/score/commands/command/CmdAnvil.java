package me.driftay.score.commands.command;

import me.driftay.score.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class CmdAnvil implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Message.NOT_PLAYER.getMessage());
            return false;
        }

        if (!sender.hasPermission("sabercore.anvil")) {
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
            return false;
        }

        Player player = (Player) sender;
        player.openInventory(Bukkit.createInventory(null, InventoryType.ANVIL));
        player.sendMessage(Message.ANVIL_OPENED.getMessage());
        return false;
    }
}

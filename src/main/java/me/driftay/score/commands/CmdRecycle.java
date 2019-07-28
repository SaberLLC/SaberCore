package me.driftay.score.commands;

import me.driftay.score.commands.handlers.PlayerHandler;
import me.driftay.score.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRecycle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("sabercore.recycle")) {
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(Message.NOT_PLAYER.getMessage());
            return true;
        }

        if (PlayerHandler.bottleRecycle.contains(sender.getName())) {
            sender.sendMessage(Message.RECYCLE_OFF.getMessage());
            PlayerHandler.bottleRecycle.remove(sender.getName());
            return true;
        }

        sender.sendMessage(Message.RECYCLE_ON.getMessage());
        PlayerHandler.bottleRecycle.add(sender.getName());
        return true;
    }
}


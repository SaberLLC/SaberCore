package me.driftay.score.commands.command;

import me.driftay.score.listeners.PlayerHandler;
import me.driftay.score.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdJellyLegs implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("sabercore.jellylegs")) {
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(Message.NOT_PLAYER.getMessage());
            return true;
        }

        if (PlayerHandler.jellyLegs.contains(sender.getName())) {
            sender.sendMessage(Message.JELLYLEGS_OFF.getMessage());
            PlayerHandler.jellyLegs.remove(sender.getName());
            return true;
        }

        sender.sendMessage(Message.JELLYLEGS_ON.getMessage());
        PlayerHandler.jellyLegs.add(sender.getName());

        return true;
    }
}

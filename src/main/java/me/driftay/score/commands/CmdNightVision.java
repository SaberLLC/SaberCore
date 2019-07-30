package me.driftay.score.commands;

import me.driftay.score.listeners.PlayerHandler;
import me.driftay.score.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdNightVision implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("sabercore.nightvision")) {
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(Message.NOT_PLAYER.getMessage());
            return true;
        }

        if (PlayerHandler.nightvision.contains(sender.getName())) {
            sender.sendMessage(Message.NIGHTVISION_OFF.getMessage());
            PlayerHandler.nightvision.remove(sender.getName());
            return true;
        }

        sender.sendMessage(Message.NIGHTVISION_ON.getMessage());
        PlayerHandler.nightvision.add(sender.getName());

        return true;
    }
}

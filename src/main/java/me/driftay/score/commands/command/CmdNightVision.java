package me.driftay.score.commands.command;

import me.driftay.score.listeners.PlayerHandler;
import me.driftay.score.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
            ((Player) sender).removePotionEffect(PotionEffectType.NIGHT_VISION);
            return true;
        }

        sender.sendMessage(Message.NIGHTVISION_ON.getMessage());
        PlayerHandler.nightvision.add(sender.getName());
        ((Player) sender).addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));

        return true;
    }
}

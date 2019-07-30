package me.driftay.score.commands;

import me.driftay.score.utils.Message;
import me.driftay.score.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdPing implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (sender instanceof Player) {
            if (args.length == 1) {
                if (!p.hasPermission("sabercore.ping.other")) {
                    p.sendMessage(Message.NO_PERMISSION.getMessage());
                    return false;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (Bukkit.getPlayer(args[0]) != null) {
                    p.sendMessage(Message.PING_OTHER.getMessage().replace("%player%", target.getName()).replace("%ping%", String.valueOf(Util.getPing(target))));
                } else {
                    p.sendMessage(Message.PLAYER_NOT_FOUND.getMessage().replace("%player%", target.getName()));
                }
            } else {
                p.sendMessage(Message.PING_YOURSELF.getMessage().replace("%ping%", String.valueOf(Util.getPing(p))));
            }
        }
        return false;
    }
}

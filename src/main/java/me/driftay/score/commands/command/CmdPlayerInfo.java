package me.driftay.score.commands.command;

import me.driftay.score.config.Conf;
import me.driftay.score.utils.Message;
import me.driftay.score.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdPlayerInfo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            if (cmd.getName().equalsIgnoreCase("playerinfo")) {
                if (sender.hasPermission("sabercore.playerinfo")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (Bukkit.getPlayer(args[0]) != null) {
                        Conf.playerInfoFormat.forEach(line -> sender.sendMessage(Util.color(line)
                                .replace("{x}", String.valueOf(Math.round(target.getLocation().getX())))
                                .replace("{y}", String.valueOf(Math.round(target.getLocation().getY())))
                                .replace("{z}", String.valueOf(Math.round(target.getLocation().getZ())))
                                .replace("{health}", String.valueOf(target.getHealth()))
                                .replace("{flyspeed}", String.valueOf(target.getFlySpeed()))
                                .replace("{ping}", String.valueOf(Util.getPing(target)))
                                .replace("{player}", target.getName())));
                    } else {
                        sender.sendMessage(Message.PLAYER_NOT_FOUND.getMessage().replace("%player%", target.getName()));
                    }
                } else {
                    sender.sendMessage(Message.NO_PERMISSION.getMessage());
                }
            }
        } else {
            Player p = (Player) sender;
            Conf.playerInfoFormat.forEach(line -> sender.sendMessage(Util.color(line)
                    .replace("{x}", String.valueOf(Math.round(p.getLocation().getX())))
                    .replace("{y}", String.valueOf(Math.round(p.getLocation().getY())))
                    .replace("{z}", String.valueOf(Math.round(p.getLocation().getZ())))
                    .replace("{health}", String.valueOf(p.getHealth()))
                    .replace("{flyspeed}", String.valueOf(p.getFlySpeed()))
                    .replace("{ping}", String.valueOf(Util.getPing(p)))
                    .replace("{player}", p.getName())));
        }
        return false;
    }
}

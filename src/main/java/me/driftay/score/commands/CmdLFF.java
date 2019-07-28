package me.driftay.score.commands;

import me.driftay.score.SaberCore;
import me.driftay.score.utils.CooldownManager;
import me.driftay.score.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CmdLFF implements CommandExecutor {

    private CooldownManager cooldownManager = new CooldownManager();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            int timeLeft = cooldownManager.getCooldown(p.getUniqueId());
            if (timeLeft == 0) {
                if (sender.hasPermission("sabercore.lff")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(Message.LFF_MESSAGE.getMessage().replace("%player%", sender.getName()));
                    }
                    if (!sender.hasPermission("sabercore.lff.bypass")) {
                        cooldownManager.setCooldown(p.getUniqueId(), CooldownManager.DEFAULT_COOLDOWN);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                int timeLeft = cooldownManager.getCooldown(p.getUniqueId());
                                cooldownManager.setCooldown(p.getUniqueId(), --timeLeft);
                                if (timeLeft == 0) {
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(SaberCore.instance, 20, 20);
                    } else {
                        cooldownManager.setCooldown(p.getUniqueId(), 0);
                    }
                } else {
                    sender.sendMessage(Message.NO_PERMISSION.getMessage());
                }
            } else {
                p.sendMessage(Message.LFF_COOLDOWN_MESSAGE.getMessage().replace("%seconds%", String.valueOf(timeLeft)));
            }
        } else {
            sender.sendMessage(Message.NOT_PLAYER.getMessage());
        }
        return false;
    }
}

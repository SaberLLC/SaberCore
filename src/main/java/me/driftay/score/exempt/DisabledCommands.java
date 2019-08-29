package me.driftay.score.exempt;

import me.driftay.score.config.Conf;
import me.driftay.score.utils.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class DisabledCommands implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        List<String> list = Conf.disabledCommands;

        for (String s : list) {
            if (message.equalsIgnoreCase(s) && !p.hasPermission("sabercore.disabledcommands.bypass")) {
                e.setCancelled(true);
                p.sendMessage(Message.DISABLED_COMMAND_MESSAGE.getMessage().replace("%command%", message.toLowerCase()));
            }
        }
    }
}

package me.driftay.score.commands.command.chat;

import me.driftay.score.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdMuteChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("sabercore.staff")){
            ChatHandler.toggleMuteChat();
            Bukkit.broadcastMessage(Message.MUTE_CHAT_TOGGLED.getMessage().replace("{context}", ChatHandler.chatMuted ? "muted" : "unmuted").replace("{player}", sender.getName()));

        } else {
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
        }
        return false;
    }
}

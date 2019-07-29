package me.driftay.score.commands;

import me.driftay.score.commands.handlers.PlayerData;
import me.driftay.score.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdCombatTag implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        PlayerData playerData = PlayerData.getByName(p.getName());

        if(!playerData.isSpawnTagActive(p)){
            sender.sendMessage(Message.COMMAND_COMBAT_TAG_NONE.getMessage());
            return false;
        }

        if(playerData.isSpawnTagActive(p)){
            sender.sendMessage(Message.COMMAND_COMBAT_TAG_TIME.getMessage().replace("%time%",PlayerData.getRemaining(playerData.getSpawnTagMillisecondsLeft(p), true, false)));
        }
        return false;
    }
}

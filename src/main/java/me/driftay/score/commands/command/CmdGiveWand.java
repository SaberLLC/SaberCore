package me.driftay.score.commands.command;

import me.driftay.score.commands.handlers.wands.impl.CraftWand;
import me.driftay.score.commands.handlers.wands.impl.LightningWand;
import me.driftay.score.commands.handlers.wands.impl.SandWand;
import me.driftay.score.utils.ItemCreation;
import me.driftay.score.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdGiveWand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!sender.isOp() || !sender.hasPermission("sabercore.wands.give")) {
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
            return false;
        }

        if (args.length == 4) {
            String arg1 = args[0];
            String player1 = args[1];
            String wandType = args[2].toLowerCase();
            String uses = args[3];
            if (arg1.equalsIgnoreCase("give")) {
                Player target = Bukkit.getPlayerExact(player1);
                if (target == null) {
                    sender.sendMessage(Message.PLAYER_NOT_FOUND.getMessage().replace("%player%", player1));
                    return false;
                }
                if(!wandType.equalsIgnoreCase("lightning")
                        && !wandType.equalsIgnoreCase("craft")
                        && !wandType.equalsIgnoreCase("sand")
                        && !wandType.equalsIgnoreCase("smelt")) {
                    sender.sendMessage(Message.WAND_USAGE.getMessage());
                    return false;
                }

                int durability;
                try {
                    durability = Integer.parseInt(uses);
                } catch (NumberFormatException ex) {
                    sender.sendMessage(Message.INVALID_NUMBER.getMessage());
                    return false;
                }

                ItemStack wand = null;
                switch(wandType) {
                    case "lightning":
                        wand = LightningWand.buildItem(durability);
                        break;
                    case "sand":
                        wand = SandWand.buildItem(durability);
                        break;
                    case "smelt":
                        wand = ItemCreation.createSmeltWandItem(durability);
                        break;
                    case "craft":
                        wand = CraftWand.buildItem(durability);
                }
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItem(player.getLocation(), wand);
                    target.sendMessage(Message.WAND_RECIEVED_NO_SPACE.getMessage());
                } else {
                    player.getInventory().addItem(wand);
                }
                target.sendMessage(Message.WAND_RECIEVED.getMessage().replace("{wand}", wandType.toLowerCase()));
            }
        } else {
            sender.sendMessage(Message.WAND_USAGE.getMessage());
        }
        return false;
    }
}

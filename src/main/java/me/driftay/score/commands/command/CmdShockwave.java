package me.driftay.score.commands.command;

import itemnbtapi.NBTItem;
import me.driftay.score.config.Conf;
import me.driftay.score.utils.Message;
import me.driftay.score.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CmdShockwave implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 4) {
            if (!sender.hasPermission("sabercore.shockwave.give")) {
                sender.sendMessage(Message.NO_PERMISSION.getMessage());
                return true;
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (args[1].equalsIgnoreCase("pickaxe") || args[1].equalsIgnoreCase("shovel") || args[1].equalsIgnoreCase("hoe")) {

                    if (Integer.parseInt(args[2]) % 2 == 0) {
                        sender.sendMessage(Util.color("&c&l[!] &7The radius need to be an odd number!"));
                        return true;
                    }
                    if (Bukkit.getPlayer(args[3]) == null || !Bukkit.getPlayer(args[3]).isOnline()) {
                        sender.sendMessage(Message.PLAYER_NOT_FOUND.getMessage());
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("shovel")) args[1] = "spade";
                    Material material = Material.getMaterial("DIAMOND_" + args[1].toUpperCase());

                    String name = Util.color(Conf.shockwaveDisplayName);
                    name = name.replace("{radius}", args[2]);
                    List<String> lore = null;
                    if (args[1].equalsIgnoreCase("pickaxe")) {
                        name = name.replace("{itemtype}", "Pickaxe");
                        lore = Util.color(Conf.shockwavePickaxeLore);
                    }
                    if (args[1].equalsIgnoreCase("spade")) {
                        name = name.replace("{itemtype}", "Shovel");
                        lore = Util.color(Conf.shockwaveShovelLore);
                    }
                    if (args[1].equalsIgnoreCase("hoe")) {
                        name = name.replace("{itemtype}", "Hoe");
                        lore = Util.color(Conf.shockwaveHoeLore);
                    }
                    if (lore != null) {
                        for (int i = 0; i <= lore.size() - 1; i++) {
                            lore.set(i, lore.get(i).replace("{radius}", args[2]));
                        }
                    }
                    ItemStack shockwaveItem = Util.createItem(material, 1, (short) 0, name, lore);
                    shockwaveItem.addUnsafeEnchantment(Enchantment.DIG_SPEED, Conf.shockwaveEfficiencyLevel);
                    shockwaveItem.addUnsafeEnchantment(Enchantment.DURABILITY, Conf.shockwaveUnbreakingLevel);
                    sender.sendMessage(Message.SHOCKWAVE_RECEIVED_MESSAGE.getMessage());
                    NBTItem nbti = new NBTItem(shockwaveItem);
                    nbti.setBoolean("Shockwave", true);
                    nbti.setInteger("Radius", Integer.parseInt(args[2]));
                    Bukkit.getPlayer(args[3]).getInventory().addItem(nbti.getItem());
                    return true;


                }
            }
        } else if (sender.hasPermission("sabercore.shockwave.give")) {
            sender.sendMessage(Message.SHOCKWAVE_COMMAND_USAGE.getMessage());
        }
        return true;
    }
}


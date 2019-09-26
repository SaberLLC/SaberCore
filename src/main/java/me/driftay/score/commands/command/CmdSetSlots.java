package me.driftay.score.commands.command;

import me.driftay.score.SaberCore;
import org.bukkit.command.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class CmdSetSlots implements CommandExecutor {

    private static final String API_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private final SaberCore plugin;

    private Object playerList;
    private Field maxPlayers;

    public CmdSetSlots(SaberCore plugin){
        try {
            Class<?> nmsPlayerListClass = Class.forName("net.minecraft.server." + API_VERSION + ".PlayerList");
            maxPlayers = nmsPlayerListClass.getDeclaredField("maxPlayers");
            maxPlayers.setAccessible(true);

            Field playerList = plugin.getServer().getClass().getDeclaredField("playerList");
            playerList.setAccessible(true);
            this.playerList = playerList.get(plugin.getServer());
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Invalid number of slots specified!");
            return false;
        }
        int slots;
        try{
            slots = Math.abs(Integer.valueOf(args[0]));
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid number of slots specified!");
            return true;
        }
        try{
            maxPlayers.set(playerList, slots);
        }catch (IllegalAccessException e){
            sender.sendMessage(ChatColor.RED + "Failed to set slots - See console for error.");
            e.printStackTrace();
            return true;
        }
        sender.sendMessage(ChatColor.YELLOW + "Successfully set the slots to " + ChatColor.BLUE + slots + ChatColor.YELLOW + ".");
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try{
                FileInputStream in = new FileInputStream("server.properties");
                Properties props = new Properties();
                props.load(in);
                in.close();

                FileOutputStream out = new FileOutputStream("server.properties");
                props.setProperty("max-players", String.valueOf(slots));
                props.store(out, null);
                out.close();
            }catch (IOException e){
                sender.sendMessage(ChatColor.RED + "Failed to update server properties, see error in console,");
                e.printStackTrace();
            }
        });
        return true;
    }
}


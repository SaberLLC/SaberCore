package me.driftay.score.utils;

import me.driftay.score.SaberCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ActionBar {

    private SaberCore instance;
    private String nmsVersion;
    private boolean oldVersion = false;

    public ActionBar(SaberCore instance) {
        this.instance = instance;
        nmsVersion = Bukkit.getServer().getClass().getPackage().getName();
        nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1);
        oldVersion = nmsVersion.startsWith("1_7_") || nmsVersion.equalsIgnoreCase("1_8_R1");
    }

    public void sendActionBar(Player player, String message) {
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
            Object craftPlayerObject = craftPlayerClass.cast(player);
            Object packet = null;
            Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmsVersion + ".PacketPlayOutChat");
            Class<?> packetClass = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");
            if (oldVersion) {
                Class<?> chatSerializerClass = Class.forName("net.minecraft.server." + nmsVersion + ".ChatSerializer");
                Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
                Method method = chatSerializerClass.getDeclaredMethod("a", String.class);
                @SuppressWarnings("unused")
                Object chatBaseComponent = iChatBaseComponentClass.cast(method.invoke(chatSerializerClass, "{\"text\": \"" + message + "\"}"));
            } else {
                Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + nmsVersion + ".ChatComponentText");
                Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
                try {
                    Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsVersion + ".ChatMessageType");
                    Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                    Object chatMessageType = null;
                    for (Object chatObject : chatMessageTypes) {
                        if (chatObject.toString().equals("GAME_INFO")) chatMessageType = chatObject;
                    }
                    Object chatComponentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                    packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass}).newInstance(chatComponentText, chatMessageType);
                } catch (ClassNotFoundException classNotFoundException) {
                    Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                    packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(chatCompontentText, (byte) 2);
                }
            }
            Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
            Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayerObject);
            Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
            Object playerConnection = playerConnectionField.get(craftPlayerHandle);
            Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
            sendPacketMethod.invoke(playerConnection, packet);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void sendActionBar(Player player, String message, int duration) {
        sendActionBar(player, Util.color(message));
        new BukkitRunnable() {
            public void run() {
                //sendActionBar(player, "");
            }
        }.runTaskLaterAsynchronously(instance, duration);
        while (duration > 40) {
            duration -= 40;
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, Util.color(message));
                }
            }.runTaskLaterAsynchronously(instance, (long) duration);
        }
    }

    public void sendActionBar(Player player, List<String> message, int duration) {
        int durationIndividual = duration / message.size();
        new BukkitRunnable() {
            int indexNumber = 0;
            public void run() {
                if (indexNumber == message.size()) {
                    cancel();
                    return;
                }
                sendActionBar(player, message.get(indexNumber++), durationIndividual);
            }
        }.runTaskTimerAsynchronously(instance, 0, durationIndividual);
    }
}


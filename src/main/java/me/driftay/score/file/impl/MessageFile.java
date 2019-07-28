package me.driftay.score.file.impl;

import me.driftay.score.SaberCore;
import me.driftay.score.file.CustomFile;
import me.driftay.score.utils.Message;

public class MessageFile extends CustomFile {

    public MessageFile() {
        super(SaberCore.instance, "", "messages");
        for (Message message : Message.values()) {
            getConfig().addDefault(message.getConfig(), message.getMessage());
        }
        getConfig().options().copyDefaults(true);
        saveConfig();
    }


    public void init() {
        for (Message message : Message.values()) {
            message.setMessage(getConfig().getString(message.getConfig()));
        }
    }
}

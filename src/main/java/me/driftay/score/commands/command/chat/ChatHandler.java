package me.driftay.score.commands.command.chat;

import me.driftay.score.utils.Util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatHandler {

    private final Pattern URL_REGEX = Pattern.compile(
            "^(http://www\\.|https://www\\.|http://|https://)?[a-z0-9]+([\\-.][a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?$");
    private final Pattern IP_REGEX = Pattern.compile(
            "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])([.,])){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");


    public static int delayTime = Util.config.getInt("slowChatTime");
    public static boolean chatMuted = false;


    public boolean shouldFilter(String message) {
        String msg = message.toLowerCase()
                .replace("3", "e")
                .replace("1", "i")
                .replace("!", "i")
                .replace("@", "a")
                .replace("7", "t")
                .replace("0", "o")
                .replace("5", "s")
                .replace("8", "b")
                .replaceAll("\\p{Punct}|\\d", "").trim();

        String[] words = msg.trim().split(" ");
        for (String word : words) {
            for (String filteredWord : Util.config.getStringList("ChatFilter.Filtered-Words")) {
                if (word.contains(filteredWord)) {
                    return true;
                }
            }
        }
        for (String word : message.replace("(dot)", ".").replace("[dot]", ".").trim().split(" ")) {
            boolean continueIt = false;
            for (String phrase : Util.config.getStringList("ChatFilter.Whitelisted-Links")) {
                if (word.toLowerCase().contains(phrase)) {
                    continueIt = true;
                    break;
                }
            }
            if (continueIt) {
                continue;
            }
            Matcher matcher = IP_REGEX.matcher(word);
            if (matcher.matches()) {
                return true;
            }
            matcher = URL_REGEX.matcher(word);
            if (matcher.matches()) {
                return true;
            }
        }
        Optional<String> optional = Util.config.getStringList("ChatFilter.Filtered-Phrases").stream().filter(msg::contains).findFirst();
        return optional.isPresent();
    }

    public static void toggleMuteChat() {
        chatMuted = !chatMuted;
    }


}

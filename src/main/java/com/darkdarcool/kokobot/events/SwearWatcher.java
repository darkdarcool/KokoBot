package com.darkdarcool.kokobot.events;

import com.darkdarcool.kokobot.User;
import com.darkdarcool.kokobot.messages.Embed;
import com.darkdarcool.kokobot.Codes;
import com.darkdarcool.kokobot.DB;
import com.darkdarcool.kokobot.events.utils.Swears;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class SwearWatcher implements Event {
    private Swears swears = new Swears();
    public boolean onMessage(MessageReceivedEvent event, DB db, User user) {
        boolean isOk = true;
        String message = event.getMessage().getContentRaw();
        ArrayList<String> badWords = swears.badWordsFound(message);
        if (badWords.size() > 0) {
            event.getMessage().delete().queue();
            // DM the user the embed
            user.sendMessage(embed(badWords));
            isOk = false;
        }
        return isOk;
    }
    private Embed embed(ArrayList<String> badWords) {
        StringBuilder sb = new StringBuilder();
        for (String word : badWords) {
            sb.append(word).append("; ");
        }
        Embed embed = new Embed("Swear found", "You have had a message blocked for swearing.\nSwears: " + sb.toString(), "Developer code: " + Codes.swearDeleted);
        return embed;
    }
}

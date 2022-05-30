package me.darkdarcool.kokobot.events;

import me.darkdarcool.kokobot.DB;
import me.darkdarcool.kokobot.User;
import me.darkdarcool.kokobot.events.utils.Swears;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class SwearWatcher implements Event {
    private Swears swears = new Swears();
    public void onMessage(MessageReceivedEvent event, DB db, User user) {
        String message = event.getMessage().getContentRaw();
        ArrayList<String> badWords = swears.badWordsFound(message);
        if (badWords.size() > 0) {
            event.getMessage().delete().queue();
            // dm the user
            event.getAuthor().openPrivateChannel().complete().sendMessage("You have had a message blocked for swearing.").queue();
        }
    }
}

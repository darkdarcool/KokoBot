package me.darkdarcool.kokobot.events;

import me.darkdarcool.kokobot.DB;
import me.darkdarcool.kokobot.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Event {
    public void onMessage(MessageReceivedEvent event, DB db, User user);
}

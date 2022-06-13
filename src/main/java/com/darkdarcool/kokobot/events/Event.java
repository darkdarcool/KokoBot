package com.darkdarcool.kokobot.events;

import com.darkdarcool.kokobot.User;
import com.darkdarcool.kokobot.DB;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Event {
    public void onMessage(MessageReceivedEvent event, DB db, User user);
}

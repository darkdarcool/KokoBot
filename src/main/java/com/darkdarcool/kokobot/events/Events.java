package com.darkdarcool.kokobot.events;

import com.darkdarcool.kokobot.User;
import com.darkdarcool.kokobot.DB;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class Events {
    ArrayList<Event> events = new ArrayList<Event>();
    public Events() {
        events.add(new SwearWatcher());
    }
    public void runEvents(MessageReceivedEvent event, DB db, User user) {
        for (Event e : events) {
            e.onMessage(event, db, user);
        }
    }
}

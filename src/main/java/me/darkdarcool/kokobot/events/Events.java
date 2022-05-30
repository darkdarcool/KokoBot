package me.darkdarcool.kokobot.events;

import me.darkdarcool.kokobot.DB;
import me.darkdarcool.kokobot.User;
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

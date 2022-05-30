package me.darkdarcool.kokobot.commands;

import me.darkdarcool.kokobot.DB;
import me.darkdarcool.kokobot.Timeout;
import me.darkdarcool.kokobot.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {

    public String getName();
    public String getDescription();
    public String getUsage();
    public void execute(MessageReceivedEvent event, DB db, User user, Timeout timeout);
}

package com.darkdarcool.kokobot.commands;

import com.darkdarcool.kokobot.User;
import com.darkdarcool.kokobot.DB;
import com.darkdarcool.kokobot.Timeout;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {

    public String getName();
    public String getDescription();
    public String getUsage();
    public void execute(MessageReceivedEvent event, DB db, User user, Timeout timeout);
    public void onInteraction(ButtonClickEvent event, DB db, User user, Timeout timeout);
}

package com.darkdarcool.kokobot.commands;

import com.darkdarcool.kokobot.User;
import com.darkdarcool.kokobot.messages.Embed;
import com.darkdarcool.kokobot.Codes;
import com.darkdarcool.kokobot.DB;
import com.darkdarcool.kokobot.Timeout;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Search implements Command {
    private final int max = 50;
    private final int min = 1;
    private final int timeOut = 20;
    @Override
    public String getName() {
        return "search";
    }

    @Override
    public String getDescription() {
        return "Search for an item :eyes:";
    }

    @Override
    public String getUsage() {
        return "&search";
    }

    @Override
    public void execute(MessageReceivedEvent event, DB db, User user, Timeout timeout) {
        timeout.setCmdName("search", user.getID());
        if (timeout.hasTimeout()) {
            int timeLeft = (Math.round(timeout.timeLeft(timeOut) / 1000) * 1000) / 1000;
            if (timeLeft <= 0) {
                timeout.remove();
            }
            else {
                event.getChannel().sendMessage("You have to wait " + timeLeft + " seconds to use this command again.").queue();
                return;
            }
        }
        else timeout.removeTimeoutIfActive();
        Random rand = new Random();
        int coins = rand.nextInt((max - min) + 1) - min;
        addCoins(coins, user);
        Embed embed = new Embed("After searching...", "You have found " + coins + " coins!\nYou now have " + user.getCoins() + " coins!", "Developer code: " + Codes.moneyAdded);
        event.getChannel().sendMessageEmbeds(embed.build()).queue();
        timeout.add(user.getID(), "search", (long) timeOut);
    }
    private void addCoins(int count, User user) {;
        user.setCoins(user.getCoins() + count);
    }
    public void onInteraction(ButtonClickEvent event, DB db, User user, Timeout timeout) {

    };
}

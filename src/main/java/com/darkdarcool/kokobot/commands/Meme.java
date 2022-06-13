package com.darkdarcool.kokobot.commands;

import com.darkdarcool.kokobot.DB;
import com.darkdarcool.kokobot.Timeout;
import com.darkdarcool.kokobot.User;
import com.darkdarcool.kokobot.messages.Embed;
import com.google.gson.JsonElement;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.darkdarcool.kokobot.utils.Web;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.interactions.components.ComponentLayout;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Meme implements Command {
    HashMap<String, String> messages = new HashMap<>(); // The user that sent it, and their id
    @Override
    public String getName() {
        return "meme";
    }

    @Override
    public String getDescription() {
        return "Get a random meme!";
    }

    @Override
    public String getUsage() {
        return "&meme";
    }

    @Override
    public void execute(MessageReceivedEvent event, DB db, User user, Timeout timeout) {

        MessageAction msg = event.getChannel().sendMessageEmbeds(generateEmbed());
        msg.setActionRow(Button.primary("meme", Emoji.fromUnicode("U+27A1\t")));
        msg.queue((message -> {
            messages.put(user.getID(), message.getId());
        }));

    }
    private MessageEmbed generateEmbed() {
        String url = Web.get("https://meme-api.herokuapp.com/gimme");
        JsonElement json = JsonParser.parseString(url);
        String title = json.getAsJsonObject().get("title").getAsString();
        String image = json.getAsJsonObject().get("url").getAsString();
        Embed embed = new Embed(title, "woah", "No developer code");
        return embed.build(image);
    }
    public void onInteraction(ButtonClickEvent event, DB db, User user, Timeout timeout) {
        String msgID = messages.get(user.getID());
        event.getChannel().editMessageEmbedsById(msgID, generateEmbed()).queue();
        event.deferEdit().queue();
    };

}

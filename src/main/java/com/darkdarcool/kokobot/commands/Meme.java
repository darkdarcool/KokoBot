package com.darkdarcool.kokobot.commands;

import com.darkdarcool.kokobot.DB;
import com.darkdarcool.kokobot.Timeout;
import com.darkdarcool.kokobot.User;
import com.darkdarcool.kokobot.messages.ImageEmbed;
import com.google.gson.JsonElement;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.darkdarcool.kokobot.utils.Web;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class Meme implements Command {
    HashMap<String, String> messages = new HashMap<>(); // The id of the message, and the user that sent it
    HashMap<String, ArrayList<ImageEmbed>> pastMemeMessages = new HashMap<>(); // The id of the message, and the memes that were sent
    HashMap<String, Integer> currentMessageMemeIndex = new HashMap<>(); // The id of the message, and the time that the message was sent
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
        ImageEmbed embed = generateEmbed();
        Collection<Button> components = new HashSet<Button>();
        components.add(Button.primary("meme:back", Emoji.fromUnicode("U+2B05\t")));
        components.add(Button.primary("meme:forward", Emoji.fromUnicode("U+27A1\t")));


        MessageAction msg = event.getChannel().sendMessageEmbeds(embed.build()).setActionRow(components);
        msg.queue((message -> {
            messages.put(message.getId(), user.getID());
            ArrayList memeMessages;
            memeMessages = new ArrayList<ImageEmbed>();
            memeMessages.add(embed);
            pastMemeMessages.put(message.getId(), memeMessages);
            currentMessageMemeIndex.put(message.getId(), -1);
        }));

    }
    private ImageEmbed generateEmbed() {
        String url = Web.get("https://meme-api.herokuapp.com/gimme");
        JsonElement json = JsonParser.parseString(url);
        String title = json.getAsJsonObject().get("title").getAsString();
        String image = json.getAsJsonObject().get("url").getAsString();
        ImageEmbed embed = new ImageEmbed(title, "woah", image);
        return embed;
    }
    public void onInteraction(ButtonClickEvent event, DB db, User user, Timeout timeout) {

        String ownerID = messages.get(event.getMessageId());
        if (ownerID == null) {
            event.reply("This isn't your own meme message! Please run the meme command to get your own!").setEphemeral(true).queue();
        }
        else if (!ownerID.equals(user.getID())) {
            event.reply("This isn't your own meme message! Please run the meme command to get your own!").setEphemeral(true).queue();
        }
        else {
            String direction = event.getButton().getId().split(":")[1];
            if (direction.equals("forward")) {
                ImageEmbed embed = generateEmbed();
                event.getChannel().editMessageEmbedsById(event.getMessageId(), embed.build()).queue();
                ArrayList memeMessages = pastMemeMessages.get(event.getMessageId());
                int index = currentMessageMemeIndex.get(event.getMessageId());
                if (index < memeMessages.size() - 1) {
                    currentMessageMemeIndex.put(event.getMessageId(), index + 1);
                }
                memeMessages.add(embed);
                pastMemeMessages.put(event.getMessageId(), memeMessages);
                event.deferEdit().queue();
            }
            if (direction.equals("back")) {
                int index = currentMessageMemeIndex.get(event.getMessageId());
                if (index <= 0) {
                    event.reply("You are at the beginning of the meme list!").setEphemeral(true).queue();
                }
                else {
                    currentMessageMemeIndex.put(event.getMessageId(), index - 1);
                    event.getChannel().editMessageEmbedsById(event.getMessageId(), pastMemeMessages.get(event.getMessageId()).get(index).build()).queue();
                    event.deferEdit().queue();
                }
            }
        }
    }


}

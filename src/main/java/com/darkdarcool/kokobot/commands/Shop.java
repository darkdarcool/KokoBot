package com.darkdarcool.kokobot.commands;

import com.darkdarcool.kokobot.DB;
import com.darkdarcool.kokobot.Timeout;
import com.darkdarcool.kokobot.User;
import com.darkdarcool.kokobot.commands.utils.Item;
import com.darkdarcool.kokobot.messages.Embed;
import com.vdurmont.emoji.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.JDA;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Shop implements Command {
    @Override
    public String getName() {
        return "shop";
    }

    @Override
    public String getDescription() {
        return "Shows all the commands KokoBot has";
    }

    @Override
    public String getUsage() {
        return "&shop";
    }

    @Override
    public void execute(MessageReceivedEvent event, DB db, User user, Timeout timeout) {
        StringBuilder shopText = new StringBuilder();
        ArrayList<Item> items = getItems();
        for(Item item : items) {
            shopText.append(buildItemString(item, event.getJDA()));
        }
        Embed embed = new Embed("Shop", shopText.toString(), "No developer code");
        event.getChannel().sendMessageEmbeds(embed.build()).queue();

    }

    public void onInteraction(ButtonClickEvent event, DB db, User user, Timeout timeout) {

    };

    private String getShop() {
        return readResourceFile("/items.txt");
    }

    private String buildItemString(Item item, JDA jda) {
        StringBuilder string = new StringBuilder();
        System.out.println(jda.getEmotesByName(item.emoji.replaceAll(":", ""), true).size());
        string.append(item.emoji).append(item.name).append(" - ").append(item.cost + " coins\n").append(item.description).append("\n\n");
        return string.toString();
    }

    private ArrayList<Item> getItems() {
        String[] lines = getShop().split("\n");
        ArrayList<String> itemIds = new ArrayList<>();
        for(String line : lines) {
            itemIds.add(line);
        }
        ArrayList<Item> items = new ArrayList<>();
        for (String item : itemIds) {
            String data = readResourceFile("/items/" + item + ".txt");
            String[] itemData = data.split("\n");
            items.add(new Item(itemData[0], itemData[1], itemData[2], itemData[3]));
        }
        return items;
    }

    private String readResourceFile(String path) {
        InputStream stream = Shop.class.getResourceAsStream(path);
        String result = new BufferedReader(new InputStreamReader(stream))
                .lines().collect(Collectors.joining("\n"));
        return result;
    }

}

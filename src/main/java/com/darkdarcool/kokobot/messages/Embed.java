package com.darkdarcool.kokobot.messages;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class Embed {
    public String title;
    public String description;
    public String footer;
    public Embed(String title, String description, String footer) {
        this.title = title;
        this.description = description;
        this.footer = footer;
    }

    public MessageEmbed build() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setDescription(description);
        eb.setFooter(footer);
        return eb.build();
    }
    public MessageEmbed build(String msgURL) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setFooter(footer);
        eb.setImage(msgURL);
        return eb.build();

    }
}

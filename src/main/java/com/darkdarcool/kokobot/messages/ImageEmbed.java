package com.darkdarcool.kokobot.messages;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class ImageEmbed {
    public String title;
    public String description;
    public String image;
    public ImageEmbed(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }
    public MessageEmbed build() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setDescription(description);
        eb.setImage(image);
        return eb.build();
    }
}

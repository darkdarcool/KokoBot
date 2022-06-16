package com.darkdarcool.kokobot.commands.utils;

public class Item {
    public String name;
    public int cost;
    public String description;
    public String emoji;

    public Item(String name, String description, String cost, String emoji) {
        this.name = name;
        this.cost = Integer.parseInt(cost);
        this.description = description;
        this.emoji = emoji;
    }



}

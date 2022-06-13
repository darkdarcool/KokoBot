package com.darkdarcool.kokobot;

import com.darkdarcool.kokobot.commands.Command;
import com.darkdarcool.kokobot.commands.Search;
import com.darkdarcool.kokobot.commands.Meme;

import java.util.HashMap;
import com.darkdarcool.kokobot.commands.Help;

public class CommandList {
    public static HashMap<String, Command> commands = new HashMap<String, Command>();
    public CommandList() {
        commands.put("help", new Help());
        commands.put("search", new Search());
        commands.put("meme", new Meme());
        // TODO: Add more commands lmao
    }
    public boolean isCommand(String command) {
        return commands.containsKey(command);
    }
    public String[] getCommandList() {
        return commands.keySet().toArray(new String[0]);
    }
    public Command getCommand(String command) {
        return commands.get(command);
    }
    public String stripContentToCommand(String message) {
        return message.split(" ")[0].replaceFirst(Config.PREFIX, "");
    }
}


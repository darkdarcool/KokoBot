package me.darkdarcool.kokobot;

import com.iwebpp.crypto.TweetNaclFast;
import me.darkdarcool.kokobot.commands.Command;

import java.util.HashMap;
import me.darkdarcool.kokobot.commands.Help;
import me.darkdarcool.kokobot.commands.Search;

public class CommandList {
    public static HashMap<String, Command> commands = new HashMap<String, Command>();
    public CommandList() {
        commands.put("help", new Help());
        commands.put("search", new Search());
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


package me.darkdarcool.kokobot.commands;

import me.darkdarcool.kokobot.DB;
import me.darkdarcool.kokobot.Timeout;
import me.darkdarcool.kokobot.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import me.darkdarcool.kokobot.CommandList;

public class Help implements Command {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Shows all the commands KokoBot has";
    }

    @Override
    public String getUsage() {
        return "&help";
    }

    @Override
    public void execute(MessageReceivedEvent event, DB db, User user, Timeout timeout) {
        CommandList cmdList = new CommandList();
        String[] commands = cmdList.getCommandList();
        StringBuilder sb = new StringBuilder();
        for(String cmd : commands) {
            sb.append(getCommandString(cmd)).append("\n");
        }
        event.getChannel().sendMessage("```" + sb.toString() + "```").queue();
    }
    private String getCommandString(String cmdName) {
        CommandList cmdList = new CommandList();
        Command cmd = cmdList.getCommand(cmdName);
        return cmd.getUsage() + ": " + cmd.getDescription();
    }
}

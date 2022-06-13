package com.darkdarcool.kokobot.commands;

import com.darkdarcool.kokobot.CommandList;
import com.darkdarcool.kokobot.DB;
import com.darkdarcool.kokobot.Timeout;
import com.darkdarcool.kokobot.User;
import com.darkdarcool.kokobot.messages.Embed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

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
        // event.getChannel().sendMessage("```" + sb.toString() + "```").queue();
        Embed embed = new Embed("Help message", sb.toString(), "No developer code");
        event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }
    private String getCommandString(String cmdName) {
        CommandList cmdList = new CommandList();
        Command cmd = cmdList.getCommand(cmdName);
        return cmd.getUsage() + ": " + cmd.getDescription();
    }
    public void onInteraction(ButtonClickEvent event, DB db, User user, Timeout timeout) {

    };

}

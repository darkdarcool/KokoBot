package me.darkdarcool.kokobot;

import me.darkdarcool.kokobot.messages.Embed;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import javax.security.auth.login.LoginException;
import java.sql.Time;
import java.util.Collection;

class Bot extends ListenerAdapter {
    private DB db = new DB();
    private Timeout timeout = new Timeout();
    private CommandList cmds = new CommandList();
    public static void main(String[] args) throws LoginException, InterruptedException {
        System.out.println("Starting bot...");
        JDA jda = JDABuilder.createDefault(Config.TOKEN).build();
        jda.addEventListener(new Bot());
        System.out.println("Bot started");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            if (event.getAuthor().isBot()) return;
            User user = new User(event.getAuthor().getId(), db);
            String content = event.getMessage().getContentRaw();
            if (content.startsWith(Config.PREFIX)) {
                boolean doesExist = user.userExists(true);
                if (!doesExist) {
                    Embed embed = new Embed("New user", Config.welcomeMessage, "Event code: " + String.valueOf(Codes.accountCreated));
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                }
                boolean isCmd = cmds.isCommand(cmds.stripContentToCommand(content));
                if (isCmd) {
                    cmds.getCommand(cmds.stripContentToCommand(content)).execute(event, db, user, timeout);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

package com.darkdarcool.kokobot;

// Local
import com.darkdarcool.kokobot.events.Events;
import com.darkdarcool.kokobot.messages.Embed;

// JDA
import com.darkdarcool.kokobot.utils.KeepAlive;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// Errors
import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {
    private DB db = new DB();
    private Timeout timeout = new Timeout();
    private final CommandList cmds = new CommandList();
    private Events events = new Events();
    public static void main(String[] args) throws LoginException {
        System.out.println("Starting bot...");
        JDA jda = JDABuilder.createDefault(Config.TOKEN).build();
        jda.addEventListener(new Bot());
        System.out.println("Bot started");
        KeepAlive.start();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            if (event.getAuthor().isBot()) return;
            User user = new User(event.getAuthor().getId(), db, event.getJDA());
            String content = event.getMessage().getContentRaw();
            if (!events.runEvents(event, db, user)) {
                return;
            };
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
    @Override
    public void onButtonClick(ButtonClickEvent event) {
        User user = new User(event.getMember().getId(), db, event.getJDA());
        String cmdName = event.getButton().getId().toString().split(":")[0];
        cmds.getCommand(cmdName).onInteraction(event, db, user, timeout);
    }



}

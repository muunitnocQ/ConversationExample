package me.danny.examples.conversations.commands;

import me.danny.examples.conversations.prompts.IntroPrompt;
import me.danny.examples.conversations.prompts.data.ConvoData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class ConversationStartCommand implements CommandExecutor {

    private final Plugin plugin;

    public ConversationStartCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage("Starting the conversation!");

        //ConversationFactory is the main way of building conversations
        //It uses the builder pattern to provide a myriad of options to the conversation
        //Generally, plugins should store a factory for each unique conversation, and then use
        //factory#buildConversation when needed. However, that's a lot of boilerplate for an example plugin
        ConversationFactory factory = new ConversationFactory(plugin)
                .withEscapeSequence("quit") //When this is entered as input, the conversation ends
                .withFirstPrompt(new IntroPrompt()) //The first prompt. Generally, always send to a MessagePrompt first so the player gets some message
                .withLocalEcho(true) //Should input be "played back" as players enter it?
                .withModality(false) //We don't want to hide chat from the player
                .withTimeout(60) //Automatic inactivity cancel
                .withPrefix((ctx) -> ChatColor.GOLD + "Conversation >> " + ChatColor.RESET); //Prepends the text to the start of every line from the conversation

        //As stated above, ideally this would be the only thing you call when you need a new conversation
        //versus making a new factory object each time

        //The cast from CommandSender to Conversable is generally safe;
        //ConsoleCommandSender and Player both implement it. However, CommandBlocks and other entities
        //also implement CommandSender. If a command block executes this command, it will throw an error
        Conversation conversation = factory.buildConversation((Conversable) sender);

        //Add an initial value to the conversation. This can be done in the ConversationFactory with #withInitialSessionData(Map<Object, Object>)
        //I prefer this method because it doesn't require you to build the map yourself. Less boilerplate
        //Since it's Map<Object, Object>, there's no constraints on what you put in here.
        //I would *HIGHLY* recommend you keep it to K=String for simplicity.
        //This is scoped to the conversation, so there is no reason to make it player-specific with UUIDs or whatever
        //data.ConvoData:
        conversation.getContext().setSessionData("data", new ConvoData());
        //</*1*>

        //Finally, to actually begin the conversation
        //Nothing will happen if you don't call this.
        //As soon as the conversation is started, the player's chat will no longer be sent to public chat,
        //and will be captured by the conversation instead
        //Another important thing of note: If modal is set to true above,
        // then players will also not receive plugin messages.
        conversation.begin();

        return true;
    }
}

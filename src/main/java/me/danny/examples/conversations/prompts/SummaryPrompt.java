package me.danny.examples.conversations.prompts;

import me.danny.examples.conversations.prompts.data.ConvoData;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//This is the final prompt in our conversation
//It will use the data we've been collecting throughout the prompts
//and send a summary to the person, and then end the conversation
public final class SummaryPrompt extends MessagePrompt {

    @Override
    protected @Nullable Prompt getNextPrompt(@NotNull ConversationContext context) {
        //This is the way conversations end gracefully
        //Use this when the conversation is done
        return Prompt.END_OF_CONVERSATION;
    }

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        ConvoData data = (ConvoData) context.getSessionData("data");
        return ChatColor.YELLOW + "Your name is %s and your age is %d! Cool! Anyways, nice talking to you! Bye!".formatted(data.getName(), data.getAge());
    }
    
}

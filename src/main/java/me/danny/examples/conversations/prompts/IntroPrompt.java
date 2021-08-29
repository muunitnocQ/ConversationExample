package me.danny.examples.conversations.prompts;

import org.bukkit.ChatColor;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

//MessagePrompt is one of the built-in prompt types.
//This prompt type is specifically for displaying messages only.
//No input can be gathered from here.
//These serve as a good first prompt for conversations.
//A few other prompts:
// - StringPrompt
// - BooleanPrompt
// - FixedSetPrompt
// - RegexPrompt
// - PlayerNamePrompt
// - NumericPrompt
//
//Danny note: In my experience, StringPrompt and MessagePrompt are what you should
//stick to. The other ones are ValidatingPrompt subclasses, and that class throws really
//ugly errors in chat when the input is not satisfied. Stick with StringPrompt and show custom errors
//on invalid input.
public final class IntroPrompt extends MessagePrompt {

    /*
       A conversation can be thought of as a graph:
                            <---------- no
                            v           ^
       Start Prompt -> Next prompt -> input good? > yes -> next prompt ...

       The general lifecycle of a conversation is as follows:
       1) Display prompt text with getPromptText
       2) Accept input (MessagePrompt excluded)
       3) Parse the input, check for validity
       4) Is the input good? If yes, continue. If no, jump to 2
       5) Is there another prompt following? If yes, jump to 1. If no, continue
       6) Perform any action based off gathered input
       7) Return Prompt.END_OF_CONVERSATION

       A good rule of thumb when designing conversations is to make a flow chart.
       Even small conversations can have 5 prompts, and they quickly add up. A flow chart will
       help you keep it organized, so you can quickly see the relationship between prompt classes.
     */
    @Override
    protected @NotNull Prompt getNextPrompt(@NotNull ConversationContext context) {
        //You can do complex logic off of the context variable here to determine where to go next,
        //but for MessagePrompts, you should generally keep it straightforward and have only one path.
        return new NamePrompt();
    }

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        //This is how you get the person who the conversation is directed at
        //Caution: If your code doesn't filter out console, this is not always a player.
        //Ensure it is using instanceof, or provide a fallback if not
        Conversable person = context.getForWhom();
        String name = "console";
        if (person instanceof Player player) {
            name = player.getDisplayName();
        }

        //This string is what's displayed when this prompt is used by the conversation
        return ChatColor.YELLOW + "Welcome to the conversation, " + name + ChatColor.YELLOW + "!";
    }
}

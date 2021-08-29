package me.danny.examples.conversations.prompts;

import me.danny.examples.conversations.prompts.data.ConvoData;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//This prompt will ask the player for their name
public final class NamePrompt extends StringPrompt {

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        //Danny note: conversations output a LOT of text to chat.
        //It might be wise to define a color scheme to keep it straightforward
        //what a prompt is versus a normal message, etc
        //In this case, light purple will the color for prompts
        return ChatColor.LIGHT_PURPLE + "What's your name?";
    }

    //This method is used to parse the input.
    //Once this method is run, the player has already entered something.
    //In here, you do general validation. Try to follow the gate-keeping pattern and return early
    //at any chance, so you know by the time you reach the bottom of the method, all has gone as you intend.
    @Override
    public @NotNull Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
        //Important:
        //A Prompt instance (this) holds state that's not directly visible to children.
        //DO NOT REUSE AN INSTANCE OF A PROMPT. ALWAYS RETURN A NEW INSTANCE.
        if (input == null) return new NamePrompt();

        //Since we're just trying to get a name, there's not really much we can do to validate
        //So we'll just impose an arbitrary restriction: Names must be more than 3 letters.
        if (input.length() <= 3) return new InvalidNameMessage(input);

        //Ok, we've validated the input. Now we're going to get the session data:
        //Care must be taken here, as the session data is just a Map<Object, Object>
        //This method will return null if the key doesn't exist, but since we know we
        //stored it during the construction of this conversation, it's fine
        ConvoData data = (ConvoData) context.getSessionData("data");

        //With the data object in hand, we can now store our data
        //A concept of note: since our data binding points to the object in the map, we don't need to
        //set the session data again.
        data.setName(input);

        //Finally, we'll progress the conversation to the next prompt.
        //Note: This method can return null. The reason for this is simple.
        //If this method returns null, the conversation ends.
        //One of the built-in prompts is Prompt.END_OF_CONVERSATION, which is
        //simply set to null.
        return new AgePrompt();
    }

    //Since you can't send messages to the player directly (or it's not really idiomatic for conversations),
    //a good model is to define concrete invalid case messages which just loop back to the calling prompt.
    //This can be extended to take a prompt in the constructor instead of returning a hardcoded prompt type
    private static final class InvalidNameMessage extends MessagePrompt {

        private final String name;

        private InvalidNameMessage(String name) {
            this.name = name;
        }

        @Override
        protected @NotNull Prompt getNextPrompt(@NotNull ConversationContext context) {
            return new NamePrompt();
        }

        @Override
        public @NotNull String getPromptText(@NotNull ConversationContext context) {
            return ChatColor.RED + "Hey! Sorry, but " + name + " is not a valid name. Names must be longer than 3 characters!";
        }
    }
}

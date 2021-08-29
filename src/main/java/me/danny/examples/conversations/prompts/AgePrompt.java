package me.danny.examples.conversations.prompts;

import me.danny.examples.conversations.prompts.data.ConvoData;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//Gets the age of the person in the conversation
//Note: I did mention there is a NumericPrompt specifically
//for getting numeric input. However, when it fails to
//validate the input as a number, it throws a really unsightly
//error in chat to the player. I highly recommend sticking with
//StringPrompt and customizing the error messages as you see fit.
public final class AgePrompt extends StringPrompt {

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        ConvoData data = (ConvoData) context.getSessionData("data");
        String name = data.getName();
        return ChatColor.LIGHT_PURPLE + "Hi, " + name + "! Nice to meet you! How old are you?";
    }

    @Override
    public @NotNull Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
        if (input == null) return new AgePrompt();

        int age;
        try {
            age = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return new InvalidAgeMessage(input);
        }

        ConvoData data = (ConvoData) context.getSessionData("data");
        data.setAge(age);
        return new SummaryPrompt();
    }

    private static final class InvalidAgeMessage extends MessagePrompt {

        private final String age;

        private InvalidAgeMessage(String age) {
            this.age = age;
        }

        @Override
        protected @NotNull Prompt getNextPrompt(@NotNull ConversationContext context) {
            return new AgePrompt();
        }

        @Override
        public @NotNull String getPromptText(@NotNull ConversationContext context) {
            return ChatColor.RED + age + " is not a valid number. Try again!";
        }
    }
}

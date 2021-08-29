package me.danny.examples.conversations;

import me.danny.examples.conversations.commands.ConversationStartCommand;
import org.bukkit.plugin.java.JavaPlugin;

//Normal plugin setup, just register a command as an entry point
//Most conversations should be started like this
//Alternatively, interacting with an NPC to start a dialog
public final class ConversationExample extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("convostart").setExecutor(new ConversationStartCommand(this));
    }

}

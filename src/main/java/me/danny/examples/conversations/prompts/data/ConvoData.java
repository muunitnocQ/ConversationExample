package me.danny.examples.conversations.prompts.data;

import org.jetbrains.annotations.NotNull;

//Another powerful feature of conversations is the concept of session data
//Each Conversation has a built-in session data variable of type Map<Object, Object>.
//The way I'd recommend you use this is by defining a custom type like so
public final class ConvoData {

    //Literally whatever
    private String name = "";
    private int age = 0;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

}
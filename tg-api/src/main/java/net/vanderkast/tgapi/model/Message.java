package net.vanderkast.tgapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.Optional;

@ToString
public class Message {
    private final Chat chat;
    private final String text;

    public Message(@JsonProperty("chat") Chat chat,
                   @JsonProperty("text") String text) {
        this.chat = chat;
        this.text = text;
    }

    public Chat getChat() {
        return chat;
    }

    public Optional<String> getText() {
        return Optional.ofNullable(text);
    }
}

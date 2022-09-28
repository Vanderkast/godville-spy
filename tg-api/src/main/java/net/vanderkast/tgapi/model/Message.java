package net.vanderkast.tgapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
public class Message {
    private final Chat chat;
    private final long messageId;
    private final String text;

    public Message(@JsonProperty("chat") Chat chat,
                   @JsonProperty("message_id") long messageId,
                   @JsonProperty("text") String text) {
        this.chat = chat;
        this.messageId = messageId;
        this.text = text;
    }

    public Optional<String> getText() {
        return Optional.ofNullable(text);
    }
}

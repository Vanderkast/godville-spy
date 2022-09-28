package net.vanderkast.tgapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DeleteMessage implements Method {
    private final long chatId;
    private final long messageId;

    public DeleteMessage(@JsonProperty("chat_id") long chatId,
                         @JsonProperty("message_id") long messageId) {
        this.chatId = chatId;
        this.messageId = messageId;
    }
}

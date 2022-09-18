package net.vanderkast.tgapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Update {
    private final long updateId;
    private final Message message;

    public Update(@JsonProperty("update_id") long updateId,
                  @JsonProperty("message") Message message) {
        this.updateId = updateId;
        this.message = message;
    }
}

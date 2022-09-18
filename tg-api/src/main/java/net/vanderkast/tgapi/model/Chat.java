package net.vanderkast.tgapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
public class Chat {
    @Getter
    private final long id;
    @Getter
    private final String type;
    private final String title;
    private final String username;

    public Chat(@JsonProperty("id") long id,
                @JsonProperty("type") String type,
                @JsonProperty("title") String title,
                @JsonProperty("username") String username) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.username = username;
    }

    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<String> getUsername() {
        return Optional.ofNullable(username);
    }
}

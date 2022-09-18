package net.vanderkast.tgapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {
    private final long id;
    private final boolean bot;
    private final String firstName;
    private final String lastName;
    private final String username;

    public User(@JsonProperty("id") long id,
                @JsonProperty("is_bot") boolean bot,
                @JsonProperty("first_name") String firstName,
                @JsonProperty("last_name") String lastName,
                @JsonProperty("username") String username) {
        this.id = id;
        this.bot = bot;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }
}

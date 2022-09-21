package net.vanderkast.tgapi;

import net.vanderkast.tgapi.model.Message;
import net.vanderkast.tgapi.model.Update;
import net.vanderkast.tgapi.model.User;

import java.util.List;
import java.util.Optional;

public interface BotApi {
    Optional<Message> send(SendMessage message);

    Optional<User> getBot();

    Optional<List<Update>> getUpdates(GetUpdates updates);
}

package net.vanderkast.godvillespy.telegrambot;

import net.vanderkast.tgapi.UpdatesLoop;
import net.vanderkast.tgapi.model.Chat;
import net.vanderkast.tgapi.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class ChatBoundHandler implements Consumer<Update> {
    private final Logger logger = LoggerFactory.getLogger(ChatBoundHandler.class);

    private final UpdatesLoop loop;
    private final String owner;
    private final CompletableFuture<Chat> chatFuture;

    private volatile boolean running = false;

    public ChatBoundHandler(UpdatesLoop loop, String owner) {
        this.loop = loop;
        this.owner = owner;
        chatFuture = new CompletableFuture<>();
    }

    public Future<Chat> getBoundChat() {
        if(!running) {
            synchronized (this) {
                if(!running)
                    loop.addListener(this);
                running = true;
            }
        }
        return chatFuture;
    }

    @Override
    public void accept(Update update) {
        if (update.getMessage() == null) {
            logger.warn("Update {} contains no message.", update.getUpdateId());
            return;
        }
        var message = update.getMessage();
        if (message.getText().isEmpty()) {
            logger.warn("Update {} contains message with no text.", update.getUpdateId());
            return;
        }
        var text = message.getText().get();
        if (text.startsWith("/bound") && message.getChat().getUsername().isPresent()) {
            var title = message.getChat().getUsername().get();
            if (title.equals(owner)) {
                chatFuture.complete(message.getChat());
                logger.info("Owner {} has bound the bot to chat.", owner);
            } else
                logger.warn("Someone tried to bound this bot to chat: {}.", title);
        } else
            logger.debug("Got incorrect message: {}", text);
    }
}

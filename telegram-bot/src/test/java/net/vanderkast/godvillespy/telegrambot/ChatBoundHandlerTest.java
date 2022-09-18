package net.vanderkast.godvillespy.telegrambot;

import net.vanderkast.tgapi.UpdatesLoop;
import net.vanderkast.tgapi.model.Chat;
import net.vanderkast.tgapi.model.Message;
import net.vanderkast.tgapi.model.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class ChatBoundHandlerTest {
    private final AtomicReference<Consumer<Update>> listener = new AtomicReference<>();
    private final String owner = "OWNER";
    private final UpdatesLoop updatesLoop = new UpdatesLoop() {
        @Override
        public void addListener(Consumer<Update> onUpdate) {
            if (listener.get() != null)
                fail("Update listener is bound already.");
            listener.set(onUpdate);
        }

        @Override
        public void close() throws Exception {
            fail("Unexpected 'close' method call.");
        }

        @Override
        public void join() throws InterruptedException {
            fail("Unexpected 'join' method call.");
        }
    };
    private final ChatBoundHandler handler = new ChatBoundHandler(updatesLoop, owner);

    @BeforeEach
    void setUp() {
        listener.set(null);
    }

    @Test
    void normal() throws ExecutionException, InterruptedException {
        var chatFuture = handler.getBoundChat();
        assertNotNull(listener.get());

        try {
            var chat = chatFuture.get(0, TimeUnit.MILLISECONDS);
            fail("Unexpected chat was returned:" + chat);
        } catch (TimeoutException e) {
            // expected behavior
        }


        var update = new Update(100, new Message(Chat.builder().username(owner).build(), "/bound"));
        listener.get().accept(update);

        try {
            var chat = chatFuture.get(0, TimeUnit.MILLISECONDS);
            assertEquals(update.getMessage().getChat(), chat);
        } catch (TimeoutException e) {
            fail("Retrieving chat failed.");
        }
    }

    @Test
    void traitor() throws ExecutionException, InterruptedException {
        var chatFuture = handler.getBoundChat();
        assertNotNull(listener.get());

        var update = new Update(100, new Message(Chat.builder().username("TRAITOR").build(), "/bound"));

        try {
            var chat = chatFuture.get(0, TimeUnit.MILLISECONDS);
            fail("Unexpected chat was returned:" + chat);
        } catch (TimeoutException e) {
            // expected behavior
        }
    }
}

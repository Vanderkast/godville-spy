package net.vanderkast.godvillespy.telegrambot.notifier;

import net.vanderkast.godvillespy.telegrambot.notification.Notification;
import net.vanderkast.tgapi.BotApi;
import net.vanderkast.tgapi.DeleteMessage;
import net.vanderkast.tgapi.SendMessage;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>This implementation of Notifier keeps single message in chat by replacing the last message with a new one.
 * Replacing is implemented via deleting the last message and sending new message.</p>
 */
public class SingleMessageNotifier implements Notifier, JobQueue<Notification> { // todo This implementation seems not accurate, mb refactor it later
    private static final long NO_MESSAGE = -1;

    private final BotApi botApi;
    private final Long chatId;

    private final BlockingQueue<Notification> notifications = new LinkedBlockingQueue<>();

    private final Thread worker = new Thread(this::run);

    private long lastMessageId = NO_MESSAGE;

    private void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                var notification = notifications.take();
                if (lastMessageId != NO_MESSAGE)
                    botApi.deleteMessage(new DeleteMessage(chatId, lastMessageId));
                lastMessageId = NO_MESSAGE;
                botApi.send(new SendMessage(chatId, notification.getMessage()))
                        .ifPresent(response -> lastMessageId = response.getMessageId());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public SingleMessageNotifier(BotApi botApi, long chatId) {
        this.botApi = botApi;
        this.chatId = chatId;
    }

    @Override
    public void handle(Notification notification) {
        if (!worker.isAlive()) {
            synchronized (worker) {
                if (!worker.isAlive()) {
                    worker.start();
                }
            }
        }
        notifications.add(notification);
    }

    @Override
    public void join() throws InterruptedException {
        worker.join();
    }

    @Override
    public void stop() throws InterruptedException {
        worker.interrupt();
    }

    @Override
    public Collection<Notification> getQueue() {
        return Collections.unmodifiableCollection(notifications);
    }
}

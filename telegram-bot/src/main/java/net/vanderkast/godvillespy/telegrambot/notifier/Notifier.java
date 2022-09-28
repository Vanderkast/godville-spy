package net.vanderkast.godvillespy.telegrambot.notifier;

import net.vanderkast.godvillespy.core.Worker;
import net.vanderkast.godvillespy.telegrambot.notification.Notification;

public interface Notifier extends Worker {
    void handle(Notification notification);

    @Override
    default void join() throws InterruptedException {
        // not implemented
    }

    @Override
    default void stop() throws InterruptedException {
        // not implemented
    }
}

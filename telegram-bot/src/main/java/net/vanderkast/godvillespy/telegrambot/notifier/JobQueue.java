package net.vanderkast.godvillespy.telegrambot.notifier;

import java.util.Collection;

public interface JobQueue<T> {
    Collection<T> getQueue();
}

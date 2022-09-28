package net.vanderkast.godvillespy.telegrambot.notifier;

import net.vanderkast.godvillespy.telegrambot.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class NotificationRouter implements Notifier {
    private static final Logger logger = LoggerFactory.getLogger(NotificationRouter.class);
    private static final Notifier STUB_NOTIFIER = notification ->
            logger.warn("No route for notification {}.", notification);

    private final Map<Class<? extends Notification>, Notifier> routes;

    public NotificationRouter(Map<Class<? extends Notification>, Notifier> routes) {
        this.routes = routes;
    }

    public static class Builder {
        private Class<? extends Notification>[] notifications;
        private Notifier[] notifiers;

        public Builder notifications(Class<? extends Notification>... notifications) {
            this.notifications = notifications;
            return this;
        }

        public Builder routes(Notifier... notifiers) {
            this.notifiers = notifiers;
            return this;
        }

        public NotificationRouter build() {
            if (notifications == null || notifiers == null || notifications.length != notifiers.length)
                throw new IllegalStateException("Length of notification types and notifiers must be the same");
            var size = notifications.length;
            Map<Class<? extends Notification>, Notifier> routes = new HashMap<>(size);
            for (int i = 0; i < size; i++)
                routes.put(notifications[i], notifiers[i]);
            return new NotificationRouter(routes);
        }
    }

    @Override
    public void handle(Notification notification) {
        routes.getOrDefault(notification.getClass(), STUB_NOTIFIER).handle(notification);
    }
}

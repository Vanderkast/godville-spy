package net.vanderkast.godvillespy.telegrambot.notifier;

import net.vanderkast.godvillespy.telegrambot.notification.HpNotification;
import net.vanderkast.godvillespy.telegrambot.notification.TokenExpiredNotification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class NotificationRouterTest {
    @Test
    void buildNormal() {
        new NotificationRouter.Builder()
                .notifications(HpNotification.class, TokenExpiredNotification.class)
                .routes(mock(Notifier.class), mock(Notifier.class))
                .build();
    }
}

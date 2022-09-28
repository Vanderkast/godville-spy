package net.vanderkast.godvillespy.telegrambot.herostatehandler;

import net.vanderkast.godvillespy.core.HeroState;
import net.vanderkast.godvillespy.telegrambot.notification.Notification;
import net.vanderkast.godvillespy.telegrambot.notifier.Notifier;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HeroStateHandlerChainTest {
    @Test
    void normal() {
        var handlers = List.of(mock(HeroStateHandler.class), mock(HeroStateHandler.class));
        var notifier = mock(Notifier.class);
        var chain = new HeroStateHandlerChain(notifier, handlers);

        var state = mock(HeroState.class);
        var notification = mock(Notification.class);
        doReturn(Optional.empty()).when(handlers.get(0)).map(state);
        doReturn(Optional.of(notification)).when(handlers.get(1)).map(any());

        chain.accept(state);

        verify(notifier).handle(notification);
    }
}

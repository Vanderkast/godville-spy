package net.vanderkast.godvillespy.telegrambot.notifier;

import net.vanderkast.godvillespy.telegrambot.notification.Notification;
import net.vanderkast.tgapi.BotApi;
import net.vanderkast.tgapi.DeleteMessage;
import net.vanderkast.tgapi.model.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Optional;

import static org.mockito.Mockito.*;

class SingleMessageNotifierTest {

    @Test
    @Timeout(2)
    void normal() throws InterruptedException {
        var api = mock(BotApi.class);
        var chatId = 1L;
        var notifier = new SingleMessageNotifier(api, chatId);
        var n1 = aNotification();

        var sentMessage = new Message(null, 100L, n1.getMessage());
        doReturn(Optional.of(sentMessage)).when(api).send(any());

        notifier.handle(n1);

        waitHandled(notifier);

        verify(api).send(argThat(msg -> msg.getText().equals(n1.getMessage())));
        verifyNoMoreInteractions(api);

        var n2 = aNotification();
        notifier.handle(n2);

        waitHandled(notifier);

        verify(api).send(argThat(msg -> msg.getText().equals(n2.getMessage())));
        verify(api).deleteMessage(new DeleteMessage(chatId, sentMessage.getMessageId()));
    }

    private Notification aNotification() {
        var message = "Hello, World! " +  System.currentTimeMillis();
        var notification = mock(Notification.class);
        doReturn(message).when(notification).getMessage();
        return notification;
    }

    private void waitHandled(SingleMessageNotifier notifier) throws InterruptedException {
        while (!notifier.getQueue().isEmpty()) {
            Thread.onSpinWait();
            Thread.sleep(20);
        }
    }
}

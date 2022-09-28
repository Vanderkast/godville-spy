package net.vanderkast.godvillespy.telegrambot.notifier;

import net.vanderkast.godvillespy.telegrambot.notification.Notification;
import net.vanderkast.tgapi.BotApi;
import net.vanderkast.tgapi.SendMessage;

public class SimpleBotMessageNotifier implements Notifier {
    private final BotApi botApi;
    private final long chatId;

    public SimpleBotMessageNotifier(BotApi botApi, long chatId) {
        this.botApi = botApi;
        this.chatId = chatId;
    }

    @Override
    public void handle(Notification notification) {
        botApi.send(new SendMessage(chatId, notification.getMessage()));
    }
}

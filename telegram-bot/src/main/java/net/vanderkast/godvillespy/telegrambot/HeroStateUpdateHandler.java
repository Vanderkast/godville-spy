package net.vanderkast.godvillespy.telegrambot;

import net.vanderkast.godvillespy.core.HeroState;
import net.vanderkast.tgapi.BotApi;
import net.vanderkast.tgapi.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class HeroStateUpdateHandler implements Consumer<HeroState> {
    private static final Logger logger = LoggerFactory.getLogger(HeroStateUpdateHandler.class);

    private final BotApi api;
    private final long chatId;
    private final int notifyOnHealthLessThanPercents;

    public HeroStateUpdateHandler(BotApi api, long chatId, int notifyOnHealthLessThanPercents) {
        this.api = api;
        this.chatId = chatId;
        this.notifyOnHealthLessThanPercents = notifyOnHealthLessThanPercents;
    }

    @Override
    public void accept(HeroState state) {
        logger.debug("Got hero state update: {}", state);
        if (state.getHealth().isEmpty()) {
            api.send(new SendMessage(chatId,
                    "Can't retrieve hero's health from Godville server. Access token is expired."));
        }
        if (shouldNotify(state))
            api.send(new SendMessage(chatId, state.toString()));
    }

    private boolean shouldNotify(HeroState state) {
        return state.getHealth().get() / state.getMaxHealth() * 100 - notifyOnHealthLessThanPercents < 0;
    }
}

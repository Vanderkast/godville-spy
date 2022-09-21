package net.vanderkast.godvillespy.telegrambot;

import net.vanderkast.godvillespy.core.HeroState;
import net.vanderkast.tgapi.BotApi;
import net.vanderkast.tgapi.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class HeroStateUpdateHandler implements Consumer<HeroState> {
    static final String ACCESS_TOKEN_IS_EXPIRED_MESSAGE
            = "Can't retrieve hero's health from Godville server. Access token is expired.";

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
            logger.warn(ACCESS_TOKEN_IS_EXPIRED_MESSAGE);
            api.send(new SendMessage(chatId, ACCESS_TOKEN_IS_EXPIRED_MESSAGE));
            return;
        }
        if (shouldNotify(state.getHealth().get(), state.getMaxHealth()))
            api.send(new SendMessage(chatId, state.toString()));
    }

    private boolean shouldNotify(float hp, int maxHp) {
        return (hp / maxHp) * 100 - notifyOnHealthLessThanPercents < 0;
    }
}

package net.vanderkast.godvillespy.telegrambot.herostatehandler;

import net.vanderkast.godvillespy.core.HeroState;
import net.vanderkast.godvillespy.telegrambot.notification.TokenExpiredNotification;
import net.vanderkast.godvillespy.telegrambot.notification.Notification;

import java.util.Optional;

public class TokenExpiredHandler implements HeroStateHandler{
    @Override
    public Optional<Notification> map(HeroState state) {
        if(state.getHealth().isEmpty())
            return Optional.of(new TokenExpiredNotification());
        return Optional.empty();
    }
}

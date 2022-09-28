package net.vanderkast.godvillespy.telegrambot.herostatehandler;

import net.vanderkast.godvillespy.core.HeroState;
import net.vanderkast.godvillespy.telegrambot.notification.HpNotification;
import net.vanderkast.godvillespy.telegrambot.notification.Notification;

import java.util.Optional;

public class HeroLowHpHandler implements HeroStateHandler {
    private final int notifyOnHealthLessThanPercents;

    public HeroLowHpHandler(int notifyOnHealthLessThanPercents) {
        this.notifyOnHealthLessThanPercents = notifyOnHealthLessThanPercents;
    }

    @Override
    public Optional<Notification> map(HeroState state) {
        if(state.getHealth().isEmpty())
            return Optional.empty();
        int hp = state.getHealth().get();
        int maxHp = state.getMaxHealth();
        if(((float) hp) / maxHp * 100 - notifyOnHealthLessThanPercents < 0)
            return Optional.of(new HpNotification(hp, maxHp));
        return Optional.empty();
    }
}

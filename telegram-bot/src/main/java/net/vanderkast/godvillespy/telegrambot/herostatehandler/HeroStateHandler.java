package net.vanderkast.godvillespy.telegrambot.herostatehandler;

import net.vanderkast.godvillespy.core.HeroState;
import net.vanderkast.godvillespy.telegrambot.notification.Notification;

import java.util.Optional;

public interface HeroStateHandler {
    Optional<Notification> map(HeroState state);
}

package net.vanderkast.godvillespy.telegrambot.notification;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HpNotification implements Notification {
    private final int hp;
    private final int maxHp;
    private final String message;

    public HpNotification(int hp, int maxHp) {
        this.hp = hp;
        this.maxHp = maxHp;
        message = "HP: " + hp + "/" + maxHp;
    }
}

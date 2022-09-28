package net.vanderkast.godvillespy.telegrambot.notification;

import lombok.ToString;

@ToString
public class TokenExpiredNotification implements Notification {
    public static final String MESSAGE = "Can't retrieve hero's health from Godville server. Access token is expired.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

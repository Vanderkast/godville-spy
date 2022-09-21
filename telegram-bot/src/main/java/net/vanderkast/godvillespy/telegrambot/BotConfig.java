package net.vanderkast.godvillespy.telegrambot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.vanderkast.godvillespy.core.Configuration;

import java.util.Optional;
import java.util.Properties;

@RequiredArgsConstructor
@Getter
public class BotConfig implements Configuration {
    public static final String BOT_TOKEN = "bot.token";
    public static final String BOT_OWNER_USERNAME = "bot.owner";
    public static final String NOTIFY_ON_HEALTH_PERCENTS = "bot.notification.on-health-percents";
    public static final String GOD_NAME = "god.name";
    public static final String ACCESS_TOKEN = "god.token";

    private final String botToken;
    private final String botOwnerUsername;
    private final int notifyOnHealthLessThanPercents;
    private final String godName;
    private final String accessToken;

    public static BotConfig from(Properties properties) throws ConfigurationException {
        return new BotConfig(
                read(properties, BOT_TOKEN),
                read(properties, BOT_OWNER_USERNAME),
                Integer.parseInt(read(properties, NOTIFY_ON_HEALTH_PERCENTS)),
                read(properties, GOD_NAME),
                read(properties, ACCESS_TOKEN));
    }

    private static String read(Properties properties, String key) throws ConfigurationException {
        var value = properties.getProperty(key);
        if (value == null)
            throw new ConfigurationException("Property " + key +" must be specified.");
        return value;
    }

    @Override
    public String getGodName() {
        return this.godName;
    }

    @Override
    public Optional<String> getAccessToken() {
        return Optional.of(accessToken);
    }

    public static class ConfigurationException extends Exception {
        public ConfigurationException(String message) {
            super(message);
        }
    }
}

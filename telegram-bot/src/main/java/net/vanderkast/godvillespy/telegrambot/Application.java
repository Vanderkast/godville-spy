package net.vanderkast.godvillespy.telegrambot;

import net.vanderkast.godvillespy.core.PeriodicGodvilleSpyImpl;
import net.vanderkast.godvillespy.telegrambot.herostatehandler.HeroLowHpHandler;
import net.vanderkast.godvillespy.telegrambot.herostatehandler.HeroStateHandlerChain;
import net.vanderkast.godvillespy.telegrambot.herostatehandler.TokenExpiredHandler;
import net.vanderkast.godvillespy.telegrambot.notification.HpNotification;
import net.vanderkast.godvillespy.telegrambot.notification.TokenExpiredNotification;
import net.vanderkast.godvillespy.telegrambot.notifier.NotificationRouter;
import net.vanderkast.godvillespy.telegrambot.notifier.SingleMessageNotifier;
import net.vanderkast.tgapi.*;
import net.vanderkast.tgapi.model.Chat;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private static final String CONFIG_PATH_ENV = "CONFIG_PATH";

    public static void main(String[] args) throws InterruptedException {
        logger.info("Bot is starting.");

        BotConfig config;
        try {
            config = readFromProperties();
        } catch (BotConfig.ConfigurationException e) {
            logger.error("Bot configuration failed.", e);
            return;
        }

        BotApi api = new TelegramBotApi(
                config.getBotToken(),
                new OkHttpClient(),
                Jackson.initialize());

        var apiBot = api.getBot();
        if (apiBot.isEmpty()) {
            logger.error("Getting info about bot is failed");
            return;
        }
        var bot = apiBot.get();
        logger.info("I'm {}", bot.getUsername());

        Chat chat;
        UpdatesLoop updatesLoop = new UpdatesLoopImpl(api);
        try {
            chat = new ChatBoundHandler(updatesLoop, config.getBotOwnerUsername()).getBoundChat().get();
        } catch (ExecutionException e) {
            logger.error("Waiting user to bound.", e);
            return;
        }
        updatesLoop.stop();

        logger.info("Bot will send updates to user {} to chat {}", config.getBotOwnerUsername(), chat);

        var notificationRouter = new NotificationRouter.Builder()
                .notifications(HpNotification.class, TokenExpiredNotification.class)
                .routes(new SingleMessageNotifier(api, chat.getId()), new SingleMessageNotifier(api, chat.getId()))
                .build();

        var stateHandler = new HeroStateHandlerChain(notificationRouter,
                List.of(new HeroLowHpHandler(config.getNotifyOnHealthLessThanPercents()),
                        new TokenExpiredHandler()));

        logger.info("Binding {} for God {} to chat {}.", bot.getUsername(), config.getGodName(), chat.getId());
        var spy = new PeriodicGodvilleSpyImpl(
                new GodvilleRequestHandler(
                        new OkHttpClient(),
                        Jackson.initialize(),
                        "https://godville.net/gods/api",
                        config));
        spy.spy(stateHandler);
    }

    public static BotConfig readFromProperties() throws BotConfig.ConfigurationException {
        Properties properties = new Properties();
        var path = System.getenv(CONFIG_PATH_ENV);
        if (path == null)
            throw new BotConfig.ConfigurationException("Specify application config file location with environment variable `" + CONFIG_PATH_ENV + "`");
        try (var file = new FileInputStream(path)) {
            properties.load(file);
        } catch (IOException e) {
            throw new BotConfig.ConfigurationException("Failed reading application properties from path " + path);
        }
        return BotConfig.from(properties);
    }
}

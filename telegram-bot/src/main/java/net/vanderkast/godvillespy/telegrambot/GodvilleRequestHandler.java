package net.vanderkast.godvillespy.telegrambot;

import net.vanderkast.godvillespy.core.Configuration;
import net.vanderkast.godvillespy.core.HeroState;
import net.vanderkast.godvillespy.core.RequestHandler;
import net.vanderkast.tgapi.Jackson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.function.Consumer;

public class GodvilleRequestHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(GodvilleRequestHandler.class);

    private final OkHttpClient client;
    private final Jackson jackson;
    private final String godEndpoint;

    public GodvilleRequestHandler(OkHttpClient client, Jackson jackson, String gameUrl, Configuration configuration) {
        this.client = client;
        this.jackson = jackson;
        godEndpoint = gameUrl + "/" + configuration.getGodName() + "/" + configuration.getAccessToken().orElse("");
    }

    @Override
    public void call(Consumer<HeroState> onStateUpdate) {
        try {
            Response response = client.newCall(new Request.Builder().url(godEndpoint).get().build()).execute();
            if (response.isSuccessful() && response.body() != null) {
                jackson.read(response.body().bytes(), HeroStateResponse.class)
                        .ifPresent(onStateUpdate);
            } else
                logger.error("Got bad response {} on getting info about character. Headers: {}", response.code(), response.headers());
        } catch (IOException e) {
            logger.error("Getting information about character failed.", e);
        }
    }
}

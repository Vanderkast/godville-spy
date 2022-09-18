package net.vanderkast.tgapi;

import com.fasterxml.jackson.core.type.TypeReference;
import net.vanderkast.tgapi.model.*;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TelegramBotApi implements BotApi {
    private static final MediaType APPLICATION_JSON = MediaType.parse("application/json");
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotApi.class);

    private final String token;
    private final OkHttpClient client;
    private final Jackson jackson;

    public TelegramBotApi(String token, OkHttpClient client, Jackson jackson) {
        this.token = token;
        this.client = client;
        this.jackson = jackson;
    }

    @Override
    public Optional<Message> send(SendMessage message) {
        return jackson.serialize(message)
                .flatMap(data -> send(getUrl(message), data))
                .flatMap(response -> parseResponse(response, new TypeReference<TgResponse<Message>>() {
                }))
                .map(TgResponse::getResult);
    }

    @Override
    public Optional<User> getBot() {
        return send(getUrl(new GetMe()), "")
                .flatMap(response -> parseResponse(response, new TypeReference<TgResponse<User>>() {
                }))
                .map(TgResponse::getResult);
    }

    @Override
    public Optional<List<Update>> getUpdates(GetUpdates updates) {
        return jackson.serialize(updates)
                .flatMap(data -> send(getUrl(updates), data))
                .flatMap(response -> parseResponse(response, new TypeReference<TgResponse<List<Update>>>() {
                }))
                .map(TgResponse::getResult);
    }

    @Override
    public Optional<List<Chat>> getAllChats() {
        return send(getUrl(new GetAllChats()), "")
                .flatMap(response -> parseResponse(response, new TypeReference<TgResponse<List<Chat>>>() {
                }))
                .map(TgResponse::getResult);
    }


    public String getUrl(Method method) {
        return "https://api.telegram.org/bot" + token + "/" + method.getName();
    }

    private Optional<Response> send(String url, String data) {
        try {
            return Optional.of(client.newCall(new Request.Builder()
                            .url(url)
                            .post(RequestBody.create(data, APPLICATION_JSON))
                            .build())
                    .execute());
        } catch (IOException e) {
            logger.error("Performing request to Telegram server failed.", e);
            return Optional.empty();
        }
    }

    private <T> Optional<T> parseResponse(Response response, TypeReference<T> ref) {
        try {
            if (response.isSuccessful() && response.body() != null) {
                return jackson.read(response.body().bytes(), ref);
            }
        } catch (IOException e) {
            logger.error("Parsing response body from Telegram API sever is failed.", e);
        }
        return Optional.empty();
    }
}

package net.vanderkast.godvillespy.telegrambot;

import net.vanderkast.godvillespy.core.HeroState;
import net.vanderkast.godvillespy.core.HeroStateDto;
import net.vanderkast.tgapi.BotApi;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class HeroStateUpdateHandlerTest {
    private final BotApi botApi = mock(BotApi.class);
    private final long chatId = 111L;
    private final int hpBarrier = 10;
    private final HeroStateUpdateHandler handler = new HeroStateUpdateHandler(botApi, chatId, hpBarrier);

    @Test
    void noGodvilleAccessToken() {
        HeroState state = new HeroStateDto("Tony", 100, null);

        handler.accept(state);
        verify(botApi).send(
                argThat(message -> message.getText().contains(HeroStateUpdateHandler.ACCESS_TOKEN_IS_EXPIRED_MESSAGE)));

        verifyNoMoreInteractions(botApi);
    }

    @Test
    void heroHpLessThanBarrier() {
        HeroState state = new HeroStateDto("Tony", 100, 7);

        handler.accept(state);
        // this test allows a token expired message to be sent, but it is tested in noGodvilleAccessToken
        verify(botApi).send(any());

        verifyNoMoreInteractions(botApi);
    }

    @Test
    void heroHpHigherThanBarrier() {
        HeroState state = new HeroStateDto("Tony", 100, 100);

        handler.accept(state);
        verifyNoInteractions(botApi);
    }
}

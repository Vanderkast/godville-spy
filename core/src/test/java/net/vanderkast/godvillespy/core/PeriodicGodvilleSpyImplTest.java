package net.vanderkast.godvillespy.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class PeriodicGodvilleSpyImplTest {
    private final RequestHandler handler = mock(RequestHandler.class);

    /**
     * This is a deceitful test because it relies on Thread#sleep correct and accurate execution.
     * Enabled if METICULOUS_TEST environment variable is set to true.
     */
    @Test
    @Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
    @EnabledIfEnvironmentVariable(
            named = "METICULOUS_TEST",
            matches = "true",
            disabledReason = "PeriodicGodvilleSpyImplTest#delay test disabled due to instability.")
    void delay() throws InterruptedException {
        var delay = 50L; // 50 ms
        final var counter = new AtomicInteger();
        var handler = new RequestHandler() {
            @Override
            public void call(Consumer<HeroState> state) {
                counter.incrementAndGet();
            }
        };
        var spy = new PeriodicGodvilleSpyImpl(delay, handler);
        spy.spy(null);
        Thread.sleep(delay * 2 - delay / 2);
        assertEquals(2, counter.get());
    }
}

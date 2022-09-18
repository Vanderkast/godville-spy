package net.vanderkast.godvillespy.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * This is stateful implementation!
 * It is designed to be started once via @see PeriodicGodvilleSpyImpl#spy(Consumer).
 */
public class PeriodicGodvilleSpyImpl implements GodvilleSpy {
    public static final long DEFAULT_PERIOD = 60_000L; // 1 min

    private static final Logger logger = LoggerFactory.getLogger(PeriodicGodvilleSpyImpl.class);

    private final long requestPeriodMillis;
    private final RequestHandler requestHandler;

    private volatile Thread spy;

    public PeriodicGodvilleSpyImpl(RequestHandler requestHandler) {
        requestPeriodMillis = DEFAULT_PERIOD;
        this.requestHandler = requestHandler;
    }

    public PeriodicGodvilleSpyImpl(long requestPeriodMillis, RequestHandler requestHandler) {
        this.requestPeriodMillis = requestPeriodMillis;
        this.requestHandler = requestHandler;
    }

    /**
     * @param onStateUpdated hero state updates consumer.
     * @return
     */
    @Override
    public boolean spy(Consumer<HeroState> onStateUpdated) {
        if (spy != null)
            return false;
        synchronized (this) {
            if (spy != null)
                return false;
            logger.info("Starting.");
            spy = new Thread(() -> run(onStateUpdated));
            spy.start();
        }
        return true;
    }

    @SuppressWarnings("BusyWait")
    public void run(Consumer<HeroState> onStateUpdated) {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                long nextCall = System.currentTimeMillis() + requestPeriodMillis;
                requestHandler.call(onStateUpdated);
                long now = System.currentTimeMillis();
                if (now < nextCall)
                    Thread.sleep(nextCall - now);
            }
        } catch (InterruptedException e) {
            logger.info("Spy was interrupted.");
            Thread.currentThread().interrupt();
        }
    }

    public boolean isAlive() {
        return spy != null && spy.isAlive();
    }

    public void stop() throws InterruptedException {
        logger.info("Stopping.");
        if (spy != null) {
            spy.interrupt();
            spy.join();
        } else
            logger.warn("Spy was stopped before start.");
    }

    @Override
    public void join() throws InterruptedException {
        spy.join();
    }
}
